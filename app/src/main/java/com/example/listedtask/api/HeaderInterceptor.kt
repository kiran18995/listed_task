package com.example.listedtask.api

import com.example.listedtask.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = MyApplication.sharedPreferences.getString("token", "")
        val request = chain.request().newBuilder().addHeader(
                "Authorization", "Bearer $token"
            ).build()
        return chain.proceed(request)
    }
}