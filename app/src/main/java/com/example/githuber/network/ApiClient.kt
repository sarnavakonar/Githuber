package com.example.githuber.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClientWithLogging())
            .build()
        return retrofit!!
    }

    private fun getOkHttpClientWithLogging(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val loggingBody = HttpLoggingInterceptor()
        loggingBody.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingBody)
        httpClient
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return httpClient.build()
    }
}