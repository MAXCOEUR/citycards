package com.example.citycards.RetrofitAPi

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL: String = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities/"

    private val gson : Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val httpClient : OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()

                val request = original.newBuilder()
                    .header("X-RapidAPI-Key", "28911db767msha7f0ad07554072fp110999jsna92ff89a4cbc")
                    .header("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
                    .method(original.method(), original.body())
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

    val ApiService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}

