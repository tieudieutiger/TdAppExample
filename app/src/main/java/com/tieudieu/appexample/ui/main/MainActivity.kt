package com.tieudieu.appexample.ui.main

import android.support.v4.app.Fragment
import com.tieudieu.appexample.R
import com.tieudieu.appexample.core.ui.BaseToolbarTitleFsmActivity
import com.tieudieu.appexample.ui.IndexTag
import com.tieudieu.fstack.BaseFragmentStack

class MainActivity : BaseToolbarTitleFsmActivity() {

    override fun getToolbarTitle(): String {
        return "Main"
    }

    override fun getContentFrameId(): Int {
        return R.id.home_container
    }

    override fun getResLayout(): Int {
        return R.layout.activity_main
    }

    override fun initVariables() {

        super.initVariables()
    }

    override fun getHomeClass(): Class<*> {

        return MainFragment::class.java
    }

    override fun onMainScreenRequested() {

        onNewScreenRequested(IndexTag.Home.HOME, -1, null)
    }

    override fun onNewScreenRequested(indexTag: Int, typeContent: Int, obj: Object?) {

        when (indexTag) {

            IndexTag.Home.HOME -> {
                getFragmentStackManager().swapFragment(MainFragment.newInstance())
            }

            IndexTag.Home.DETAIL -> {
                getFragmentStackManager().swapFragment(DetailFragment.newInstance())
            }
        }
    }

    override fun onFragmentEntered(fragment: Fragment?) {

        if (fragment != null && fragment is BaseFragmentStack)
            setToolbarTitle(fragment.getFragmentTitle())
    }

}
