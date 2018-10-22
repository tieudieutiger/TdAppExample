package com.tieudieu.utils

import android.util.Log

class DebugLog {

    private constructor()

    companion object {

        var enable = true
        var isShowLogDetail = true
        internal var className: String = ""
        internal var methodName: String = ""
        var lineNumber: Int = 0

        fun isDebuggable() = enable

        fun createLog(log: String): String {

            var buffer = StringBuffer()

            if (isShowLogDetail) {

                buffer.append("[")
                buffer.append(methodName)
                buffer.append(":")
                buffer.append(lineNumber)
                buffer.append("]")
            }

            buffer.append(log)

            return buffer.toString()
        }

        private fun getMethodNames(sElements: Array<StackTraceElement>) {
            className = sElements[1].fileName
            methodName = sElements[1].methodName
            lineNumber = sElements[1].lineNumber
        }

        fun e(message: String) {
            if (!isDebuggable())
                return

            // Throwable instance must be created before any methods
            getMethodNames(Throwable().stackTrace)
            Log.e(className, createLog(message))
        }

        fun i(message: String) {
            if (!isDebuggable())
                return

            getMethodNames(Throwable().stackTrace)
            Log.i(className, createLog(message))
        }

        fun v(message: String) {
            Log.v(className, createLog(message))
        }

        fun w(message: String) {
            if (!isDebuggable())
                return

            getMethodNames(Throwable().stackTrace)
            Log.w(className, createLog(message))
        }

        fun wtf(message: String) {
            if (!isDebuggable())
                return

            getMethodNames(Throwable().stackTrace)
            Log.wtf(className, createLog(message))
        }

        fun d(`object`: Any) {
            if (!isDebuggable())
                return

            getMethodNames(Throwable().stackTrace)
            Log.d(className, createLog(`object`.toString()))
        }
    }

}