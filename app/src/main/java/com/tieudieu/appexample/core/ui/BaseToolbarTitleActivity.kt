package com.tieudieu.appexample.core.ui

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import com.tieudieu.appexample.R

import com.tieudieu.fstack.BaseActivity
import kotlinx.android.synthetic.main.base_toolbar_title_activity.*

abstract class BaseToolbarTitleActivity : BaseActivity() {

    override fun setContentView(layoutResID: Int) {
        val fullView: CoordinatorLayout =
                layoutInflater.inflate(R.layout.base_toolbar_title_activity, null) as CoordinatorLayout
        val container: FrameLayout = fullView.findViewById(R.id.container)
        layoutInflater.inflate(layoutResID, container, true)
        super.setContentView(fullView)
    }

    override fun initVariables() {
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toolbar_menu.visibility = if (isShowToolbarMenu()) View.VISIBLE else View.GONE
        if (getToolbarMenuIconRes() != 0)
            toolbar_menu.setImageResource(getToolbarMenuIconRes())

        tv_toolbar_title.text = getToolbarTitle()

        btn_toolbar_back.setOnClickListener {
//            AnimationUtil.animateView(btn_toolbar_back, object: Completion {
//                override fun onCompleted() {
                    finish()
//                }
//            })
        }

        toolbar_menu.setOnClickListener {
//            AnimationUtil.animateView(btn_toolbar_back, object: Completion {
//                override fun onCompleted() {
                    onClickedToolbarMenu()
//                }
//            })
        }
    }

    abstract fun getToolbarTitle(): String

    fun isShowToolbarMenu(): Boolean {
        return false
    }

    fun getToolbarMenuIconRes() : Int {
        return 0
    }

    protected fun onClickedToolbarMenu() {
    }

    fun setToolbarMenu(iconRes: Int) {
        toolbar_menu!!.setImageResource(iconRes)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

}