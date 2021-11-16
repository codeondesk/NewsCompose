package eu.tutorials.newsapp.data.network

import eu.tutorials.newsapp.data.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    //Todo 1 Replace retrofit call with coroutine
    @GET("top-headlines")
    suspend fun getTopArticles(@Query("country") country:String): TopNewsResponse

    //Todo 2: Replace retrofit call with coroutine
    @GET("top-headlines")
    suspend fun getArticlesByCategories(@Query("category") category:String):TopNewsResponse

    @GET("everything")
    fun getArticlesBySources(@Query("sources") source:String):Call<TopNewsResponse>


    @GET("everything")
    fun searchArticles(@Query("q") country:String):Call<TopNewsResponse>
}