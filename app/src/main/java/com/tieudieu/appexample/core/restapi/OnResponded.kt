package com.cis.iparkingpayment.core.utils.restapi

interface OnResponded<R, E> {

    fun onRespondedSuccess(tag: String?, response: R?, extraData: E?)

    fun onRespondedError(tag: String?, message: String?)

}