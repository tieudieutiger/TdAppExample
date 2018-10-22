package com.tieudieu.fstack

import android.support.v4.app.Fragment

interface ScreenManager {

    fun onMainScreenRequested()

    fun onSwapFragmentRequested(fragment: Fragment)

    fun onBackFragmentRequested()

    fun onFragmentEntered(fragment: Fragment?)

    fun onCloseRequested()

    //fun onNewScreenRequested(indexTag: Int)

    fun onNewScreenRequested(indexTag: Int, typeContent: Int, obj: Object?)

    //fun onNewScreenRequested(indexTag: Int, typeContent: String, obj: Object)

    fun onClearStackRequested()

    fun onClearStackAllRequested()

    fun onClearStackUntilTopRequested()

    fun onClearStackAllUntilTopRequested()

}