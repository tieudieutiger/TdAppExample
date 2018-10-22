package com.tieudieu.fstack

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getResLayout())

        initVariables()

        initViews(savedInstanceState)
    }

    protected abstract fun getResLayout(): Int

    protected abstract fun initVariables()

    protected abstract fun initViews(savedInstanceState: Bundle?)
}