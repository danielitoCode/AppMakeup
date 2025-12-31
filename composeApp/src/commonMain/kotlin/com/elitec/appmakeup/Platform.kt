package com.elitec.appmakeup

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform