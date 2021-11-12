package eu.tutorials.newsapp.network

import eu.tutorials.newsapp.network.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    fun getTopArticles(@Query("country") country:String): Call<TopNewsResponse>

    @GET("top-headlines")
    fun getArticlesByCategories(@Query("category") category:String):Call<TopNewsResponse>

    //Todo 5: create request for articles by sources
    @GET("everything")
    fun getArticlesBySouurces(@Query("sources") source:String):Call<TopNewsResponse>
}