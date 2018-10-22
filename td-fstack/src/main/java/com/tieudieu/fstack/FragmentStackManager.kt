package com.tieudieu.fstack

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import com.tieudieu.fstack.workqueue.WorkQueue
import com.tieudieu.utils.DebugLog
import java.util.*

class FragmentStackManager<F : Fragment> : FragmentStackSwapper<F> {

    companion object {

        const val TIME_ANIMATION: Int = 400
    }

    private lateinit var mInitializationParams: InitializationParams
    private var mUiHandler: Handler = Handler()
    private var mContentFragment: F? = null
    private lateinit var stackFragments: Stack<F>

    private var mUseWorkQueue: Boolean = false
    private lateinit var mWorkQueue: WorkQueue

    @Override
    override fun initialize(initializationParams: InitializationParams) {

        if (initializationParams == null) {
            throw IllegalArgumentException("Argument is mandatory")
        }

        mInitializationParams = initializationParams
        stackFragments = Stack()

        if (mUseWorkQueue) {
//            mWorkQueue = WorkQueue(2)
//            mWorkQueue.start()
        }

    }

    @Override
    override fun destroy() {

        if (mUseWorkQueue) {
            //mWorkQueue.stop()
        }
    }

    private fun performOperationIfAllowed(operation: Runnable) {

        mUiHandler.post { operation.run() }
    }

    private fun performOperationIfAllowed(operation: Runnable, postDelay: Boolean) {

        if (postDelay) {
            mUiHandler.postDelayed({ operation.run() }, TIME_ANIMATION.toLong())
        } else {
            performOperationIfAllowed(operation)
        }
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {

        DebugLog.e(java.lang.String.format("onRestoreInstanceState(): savedInstanceState[%b]", savedInstanceState != null))

        if (savedInstanceState == null) {
            mInitializationParams.getScreenManager().onMainScreenRequested()
        } else {
            notifyFragmentChange()
        }
    }

    private fun notifyFragmentChange() {

        if (findCurrentFragment()) {

            mUiHandler.post {

                if (mInitializationParams != null) {
                    mInitializationParams.getScreenManager().onFragmentEntered(mContentFragment)
                }
            }
        }
    }

    private fun notifyPause() {

        if (stackFragments.size > 0) {
            stackFragments.lastElement().onPause()
        }
    }

    private fun notifyCloseRequest() {
        DebugLog.v("notifyCloseRequest()")

        mUiHandler.post {

            if (mInitializationParams.getScreenManager() != null) {

                mInitializationParams.getScreenManager().onCloseRequested()
            }
        }
    }

    private fun findCurrentFragment(): Boolean {

        return if (stackFragments == null || stackFragments.size == 0) {

            false

        } else {

            mContentFragment = stackFragments.lastElement()
            mContentFragment != null
        }
    }

    @Override
    override fun size(): Int {
        return if (stackFragments == null) 0 else stackFragments.size
    }

    @Override
    override fun getCurrentFragment(): F? {
        return mContentFragment
    }

    @Override
    override fun getFragmentByTag(tag: String): F? {
        return null
    }

    @Override
    override fun swapFragment(fragment: F) {

        try {

            val operation = Runnable {
                DebugLog.v("swapFragment()")

                val ft = mInitializationParams.getFragmentManager().beginTransaction()

                if (mInitializationParams.isAnimationEnabled()) {

                    // ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_in, 0, 0);
                    ft.setCustomAnimations(R.anim.slide_left_in, 0, 0, 0)
                }

                ft.add(mInitializationParams.getContentFrame(), fragment)

                if (stackFragments.size > 0) {

                    notifyPause()

                    if (!mInitializationParams.isAnimationEnabled()) {
                        ft.hide(stackFragments.lastElement())
                    }
                }

                stackFragments.push(fragment)
                ft.commit()

                mInitializationParams.getFragmentManager().executePendingTransactions()

                findCurrentFragment()

                notifyFragmentChange()
            }

            /*if (mUseWorkQueue)
                mWorkQueue.execute(operation)
            else*/
            performOperationIfAllowed(operation)


            // hide old fragment if animation anable
            if (mInitializationParams.isAnimationEnabled()) {

                val operationHide = Runnable {

                    val ft = mInitializationParams.getFragmentManager().beginTransaction()

                    if (stackFragments.size > 1) {
                        if (mInitializationParams.isAnimationEnabled()) {
                            ft.hide(stackFragments[stackFragments.size - 2])
                        }
                    }

                    ft.commit()
                }
                //
                /*if (mUseWorkQueue) {

                    val operationHideDelay = Runnable { mUiHandler.postDelayed({ operationHide.run() }, TIME_ANIMATION.toLong()) }

                    mWorkQueue.execute(operationHideDelay)
                } else {*/

                performOperationIfAllowed(operationHide, true)
//            }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Override
    override fun popFragment() {

        try {

            val operation = Runnable {

                val stackEntries = stackFragments.size
                // DebugLog.v(String.format("popFragment(): entries[%d]", stackEntries));

                if (stackEntries >= 2) {

                    val ft = mInitializationParams.getFragmentManager().beginTransaction()

                    if (mInitializationParams.isAnimationEnabled()) {
                        ft.setCustomAnimations(0, R.anim.slide_right_out, 0, 0)
                    }

                    stackFragments.lastElement().onPause()
                    ft.remove(stackFragments.pop())
                    stackFragments.lastElement().onResume()
                    ft.show(stackFragments.lastElement())
                    ft.commit()
                    findCurrentFragment()
                    notifyFragmentChange()
                    // DebugLog.v("popFragment-fragmentStack.size()=" + stackFragments.size());
                } else {
                    notifyCloseRequest()
                }
            }

            //
            /*if (mUseWorkQueue)
                mWorkQueue.execute(operation)
            else*/
            performOperationIfAllowed(operation)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Override
    override fun clearStack() {

        try {

            if (mInitializationParams.getHomeClass() == null) {
                clearStackAll()
            } else {

                val operation = Runnable {

                    mInitializationParams.getFragmentManager().executePendingTransactions()
                    val ft = mInitializationParams.getFragmentManager().beginTransaction()

                    for (i in stackFragments.size - 1 .. 0) {

                        if (stackFragments[i]::class == mInitializationParams.getHomeClass()) {
                            stackFragments[i].onResume()
                            ft.show(stackFragments[i])
                            continue
                        }
                        ft.remove(stackFragments[i])
                        stackFragments.removeAt(i)
                    }

                    ft.commit()
                }

                //
                /*if (mUseWorkQueue)
                    mWorkQueue.execute(operation)
                else*/
                performOperationIfAllowed(operation)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Override
    override fun clearStackAll() {

        try {

            val operation = Runnable {
                mInitializationParams.getFragmentManager().executePendingTransactions()
                val ft = mInitializationParams.getFragmentManager().beginTransaction()

                for (i in stackFragments.size - 1 .. 0) {
                    ft.remove(stackFragments[i])
                    stackFragments.removeAt(i)
                }
                ft.commit()
            }

            //
            /*if (mUseWorkQueue)
                mWorkQueue.execute(operation)
            else*/
            performOperationIfAllowed(operation)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Override
    override fun clearStackUntilTop() {

        try {

            if (mInitializationParams.getHomeClass() == null) {

                clearStackAllUntilTop()

            } else {

                val operation = Runnable {
                    mInitializationParams.getFragmentManager().executePendingTransactions()
                    val ft = mInitializationParams.getFragmentManager().beginTransaction()

                    for (i in stackFragments.size - 2 .. 0) {
                        if (stackFragments[i]::class == mInitializationParams.getHomeClass()) {
                            stackFragments[i].onResume()
                            ft.show(stackFragments[i])
                            continue
                        }
                        ft.remove(stackFragments[i])
                        stackFragments.removeAt(i)
                    }
                    ft.commit()
                }

                /*if (mUseWorkQueue)
                    mWorkQueue.execute(operation)
                else*/
                performOperationIfAllowed(operation)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Override
    override fun clearStackAllUntilTop() {

        try {

            val operation = Runnable {
                mInitializationParams.getFragmentManager().executePendingTransactions()
                val ft = mInitializationParams.getFragmentManager().beginTransaction()

                for (i in stackFragments.size - 2 .. 0) {
                    ft.remove(stackFragments[i])
                    stackFragments.removeAt(i)
                }
                ft.commit()
            }

            /*if (mUseWorkQueue)
                mWorkQueue.execute(operation)
            else*/
            performOperationIfAllowed(operation)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}