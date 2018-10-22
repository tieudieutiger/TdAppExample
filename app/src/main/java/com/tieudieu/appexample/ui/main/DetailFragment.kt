package com.tieudieu.appexample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tieudieu.appexample.R
import com.tieudieu.appexample.ui.IndexTag
import com.tieudieu.fstack.BaseFragmentStack
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment: BaseFragmentStack(), View.OnClickListener {

    companion object {

        fun newInstance(): DetailFragment {

            var args = Bundle()

            var fragment = DetailFragment()

            fragment.arguments = args

            return fragment
        }
    }

    override fun getIndexTag(): Int {

        return IndexTag.Home.DETAIL
    }

    override fun getFragmentTitle(): String {

        return "Detail"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mRootView = inflater.inflate(R.layout.detail_fragment, container, false)

        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()
    }

    fun initViews() {

        btn_close_detail.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        if (v == null)
            return

        if (v.id == R.id.btn_close_detail) {
            getScreenManager().onBackFragmentRequested()
        }
    }
}