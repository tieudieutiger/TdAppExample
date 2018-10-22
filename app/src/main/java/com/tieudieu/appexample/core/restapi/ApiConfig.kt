package com.cis.iparkingpayment.core.utils.restapi

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiConfig @Inject constructor() {

    fun isDebuggable() = false

    fun isApiTest() = false

    fun getToken(): String {

        return "Token"
    }

}