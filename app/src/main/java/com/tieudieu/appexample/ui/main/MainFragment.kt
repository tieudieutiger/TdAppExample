package com.tieudieu.appexample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tieudieu.appexample.R
import com.tieudieu.appexample.ui.IndexTag
import com.tieudieu.fstack.BaseFragmentStack
import com.tieudieu.utils.DebugLog
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment: BaseFragmentStack(), View.OnClickListener {

    companion object {

        fun newInstance(): MainFragment {

            var args = Bundle()

            var fragment = MainFragment()

            fragment.arguments = args

            return fragment
        }
    }

    override fun getIndexTag(): Int {

        return IndexTag.Home.HOME
    }

    override fun getFragmentTitle(): String {

        return "Main"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mRootView = inflater.inflate(R.layout.main_fragment, container, false)

        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()
    }

    private fun initViews() {

        btn_show_detail.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        if (v == null)
            return

        if (v.id == R.id.btn_show_detail) {
            getScreenManager().onNewScreenRequested(IndexTag.Home.DETAIL, -1, null)
        }
    }
}