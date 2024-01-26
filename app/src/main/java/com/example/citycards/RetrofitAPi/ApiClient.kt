package com.example.citycards.RetrofitAPi

import android.provider.Settings.Secure.getString
import com.example.citycards.BuildConfig
import com.example.citycards.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL: String = "https://wft-geo-db.p.rapidapi.com/v1/geo/"


    private val gson : Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val httpClient : OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()

                val apiKey= BuildConfig.citycards_key

                val request = original.newBuilder()
                    .header("X-RapidAPI-Key", apiKey)
                    .header("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
                    .build()

                return chain.proceed(request)
            }
        })
            .build()
    }


    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val getApiService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}

