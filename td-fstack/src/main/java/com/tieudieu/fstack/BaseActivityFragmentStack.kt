package com.tieudieu.fstack

import android.os.Bundle
import android.support.v4.app.Fragment

abstract class BaseActivityFragmentStack : BaseActivity(), ScreenManager {

    private lateinit var mFragmentStackManager: FragmentStackManager<Fragment>

    protected abstract fun getContentFrameId(): Int

    protected abstract fun getHomeClass(): Class<*>

    override fun initVariables() {
    }

    override fun initViews(savedInstanceState: Bundle?) {

        initializeFragmentSwapper(savedInstanceState)
    }

    private fun initializeFragmentSwapper(savedInstanceState: Bundle?) {

        var builder: InitializationParams.Builder = InitializationParams.Builder()
        builder.screenManager(this)
        builder.contentFrame(getContentFrameId())
        builder.fragmentManager(supportFragmentManager)
        builder.contentFrame(getContentFrameId())
        builder.enableAnimation(false)
        builder.homeClass(getHomeClass())

        mFragmentStackManager = FragmentStackManager()
        mFragmentStackManager.initialize(builder.build())
        mFragmentStackManager.onRestoreInstanceState(savedInstanceState)

    }

    override fun onBackPressed() {

        if (mFragmentStackManager != null)
            mFragmentStackManager.popFragment()
    }

    override fun onDestroy() {

        if (mFragmentStackManager != null)
            mFragmentStackManager.destroy()

        super.onDestroy()
    }

    override fun onSwapFragmentRequested(fragment: Fragment) {
        if (mFragmentStackManager != null)
            mFragmentStackManager.swapFragment(fragment)
    }

    override fun onBackFragmentRequested() {

        if (mFragmentStackManager != null)
            mFragmentStackManager.popFragment()
    }

    override fun onCloseRequested() {
        finish()
    }

    override fun onClearStackUntilTopRequested() {

        if (mFragmentStackManager != null)
            mFragmentStackManager.clearStackUntilTop()
    }

    override fun onClearStackAllUntilTopRequested() {

        if (mFragmentStackManager != null)
            mFragmentStackManager.clearStackAllUntilTop()
    }

    override fun onClearStackRequested() {

        if (mFragmentStackManager != null)
            mFragmentStackManager.clearStack()
    }

    override fun onClearStackAllRequested() {

        if (mFragmentStackManager != null)
            mFragmentStackManager.clearStackAll()
    }

    protected fun getFragmentStackManager(): FragmentStackManager<Fragment> {

        if (mFragmentStackManager == null) {
            throw IllegalArgumentException()
        }

        return mFragmentStackManager
    }

    protected fun getCurrentFragment(): Fragment? {

        if (mFragmentStackManager == null) {
            throw IllegalArgumentException()
        }

        return mFragmentStackManager.getCurrentFragment()
    }


}