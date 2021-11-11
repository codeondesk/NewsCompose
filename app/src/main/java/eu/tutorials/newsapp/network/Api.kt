package eu.tutorials.newsapp.network

import retrofit2.Retrofit

//Todo 7: create the api class
object Api {
    //Todo 8:create the api class
    private val BASE_URL = "https://newsapi.org/v2/"

    //Todo 9: intialize retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .build()

    //Todo 10: initialize the service class
    val retrofitService: NewsService by lazy { retrofit.create(NewsService::class.java) }
}