package com.elitec.appmakeup.logs

object Logger {
    fun error(tag: String? = "", message: String, error: Throwable) {
        println("[$tag] ----> ❌ ERROR___ $message \n Cause: $error")
    }

    fun success(tag: String? = "", message: String) {
        println("[$tag] ----> ✅ $message")
    }

    fun warning(tag: String? = "", message: String) {
        println("[$tag] ----> ⚠️⚠ $message")
    }
}