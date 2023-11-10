package com.example.sweatsink.Common

import com.example.sweatsink.Remote.IGoogleAPIServices
import com.example.sweatsink.Remote.RetrofitClient

object Common {
    private val GOOGLE_API_URL="https://maps.googleapis.com/"

    val googleAPIServices:IGoogleAPIServices
        get() = RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIServices::class.java)
}