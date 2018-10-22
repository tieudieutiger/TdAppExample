package com.tieudieu.fstack

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseFragment: Fragment() {

    var mContext: Context? = null
    var mRootView: View?= null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        mContext = context
    }

}