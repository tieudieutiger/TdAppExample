package com.cis.iparkingpayment.core.utils.restapi

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Error: Serializable {

    constructor(code: Int, message: String) {

        this.code = code
        this.message = message
    }

    @SerializedName("Code") internal var code: Int = -1

    @SerializedName("Message") internal var message: String = ""


}