package com.tieudieu.fstack

import android.content.Context

abstract class BaseFragmentStack : BaseFragment() {

    private var mTitle: String? = null
    private lateinit var mScreenManager: ScreenManager

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context != null && context is ScreenManager) {

            mScreenManager = context
        } else {

            throw IllegalArgumentException()
        }
    }

    fun getScreenManager(): ScreenManager {

        return mScreenManager
    }

    fun getTitle(): String? {

        return mTitle
    }

    fun setTitle(title: String) {

        mTitle = title
    }

    open fun showBackButton(): Boolean = false

    abstract fun getFragmentTitle(): String

    abstract fun getIndexTag(): Int
}