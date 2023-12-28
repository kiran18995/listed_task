package com.example.listedtask.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date

private const val BASE_URL = "https://api.inopenapp.com/"

object RetrofitInstance {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // You can set the logging level as needed
    }

    private val client = OkHttpClient.Builder().addInterceptor(HeaderInterceptor())
        .addInterceptor(loggingInterceptor).build()

    private val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter())
        .addLast(KotlinJsonAdapterFactory()).build()

    /**
     * Use the Retrofit builder to build a retrofit object using a Moshi converter.
     */
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
    }

    val api: DashboardApi by lazy {
        retrofit.create(DashboardApi::class.java)
    }
}