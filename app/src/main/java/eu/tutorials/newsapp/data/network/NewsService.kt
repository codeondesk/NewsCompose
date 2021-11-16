package eu.tutorials.newsapp.data.network

import eu.tutorials.newsapp.data.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    @GET("top-headlines")
    suspend fun getTopArticles(@Query("country") country:String): TopNewsResponse

    @GET("top-headlines")
    suspend fun getArticlesByCategories(@Query("category") category:String):TopNewsResponse

    //Todo 1: replace Retrofit call with coroutine
    @GET("everything")
    suspend fun getArticlesBySources(@Query("sources") source:String):TopNewsResponse

    @GET("everything")
    suspend fun searchArticles(@Query("q") country:String):TopNewsResponse
}