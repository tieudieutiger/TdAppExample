package com.cis.iparkingpayment.core.utils.restapi

import retrofit2.Response

class OnRespondedHandler<R, E> {

    fun returnResponse(onResponded: OnResponded<R, E>, tag: String?, response: Response<R>?, extraData: E?) {

        if (onResponded != null && response != null) {

            when {

                response.code() == 401 ->  {
                    // unAuthorized
                }

                response.body() != null -> onResponded.onRespondedSuccess(tag, response.body(), extraData)

                else -> onResponded.onRespondedError(tag, null)

            }

        }
    }

    fun returnResponseFailure(onResponded: OnResponded<R, E>?, tag: String?, message: String?) {

        onResponded?.onRespondedError(tag, message)
    }

}