package eu.tutorials.newsapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//Todo 7: create the api class
object Api {
    //Todo 8:create the api class
    private val BASE_URL = "https://newsapi.org/v2/"

    //Todo 18: Initialize moshi and add to retrofit builder
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    //Todo 9: intialize retrofit
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    //Todo 10: initialize the service class
    val retrofitService: NewsService by lazy { retrofit.create(NewsService::class.java) }
}