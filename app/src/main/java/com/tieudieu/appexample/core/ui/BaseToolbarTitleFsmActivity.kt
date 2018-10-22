package com.tieudieu.appexample.core.ui

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.view.MenuItem
import android.widget.FrameLayout
import com.tieudieu.appexample.R
import com.tieudieu.fstack.BaseActivityFragmentStack
import kotlinx.android.synthetic.main.base_toolbar_title_activity.*

abstract class BaseToolbarTitleFsmActivity: BaseActivityFragmentStack() {

    /*override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }*/

    override fun setContentView(layoutResID: Int) {

        val fullView: CoordinatorLayout =
                layoutInflater.inflate(R.layout.base_toolbar_title_activity, null) as CoordinatorLayout

        val container: FrameLayout = fullView.findViewById(R.id.container)

        layoutInflater.inflate(layoutResID, container, true)

        super.setContentView(fullView)

    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)

        setToolbarTitle(getToolbarTitle())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId === android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    fun setToolbarTitle(title: String) {
        tv_toolbar_title.text = title
    }

    abstract fun getToolbarTitle(): String

    fun isShowToolbarMenu() = false

    fun getToolbarMenuIconRes() = 0

    protected fun onClickedToolbarMenu() = {}

}