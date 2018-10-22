package com.tieudieu.fstack

import android.support.v4.app.Fragment

interface FragmentStackSwapper<F : Fragment> {


    fun initialize(initializationParams: InitializationParams)
    fun destroy()

    fun swapFragment(fragment: F)
    fun popFragment()
    fun clearStack()
    fun clearStackAll()
    fun clearStackUntilTop()
    fun clearStackAllUntilTop()
    fun size(): Int

    fun getCurrentFragment(): F?
    fun getFragmentByTag(tag: String): F?

}