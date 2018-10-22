package com.cis.iparkingpayment.core.utils.restapi

import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ApiResponse<T>: Serializable {

    @SerializedName("Error") internal var error: Error? = null

    @SerializedName("Data") internal var data: T? = null

    fun isResponseSuccess(): Boolean = (error != null && error!!.code == 200)

    fun isResponseError(): Boolean = (error == null || error!!.code != 200)

    fun isMessageEmpty(): Boolean = TextUtils.isEmpty(getMessageError())

    fun getMessageError(): String {

        return if (error == null)
            ""
        else error!!.message
    }

}