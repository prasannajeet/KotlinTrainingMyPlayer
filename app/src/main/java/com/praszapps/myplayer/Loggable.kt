package com.praszapps.myplayer

import android.util.Log

interface Loggable {

    val tag: String
        get() = javaClass.simpleName // Can define contract properties

    fun logDebug(message: String) = Log.d(tag, message)

    fun logError(message: String) = Log.e(tag, message)

    fun log(message: String, isWarn : Boolean = false) {

        if(isWarn) {
            Log.w(tag, message)
        } else {
            logDebug(message)
        }
    }
}