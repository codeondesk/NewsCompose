package eu.tutorials.newsapp.network

import eu.tutorials.newsapp.network.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    fun getTopArticles(@Query("country") country:String, @Query("apiKey")apiKey:String): Call<TopNewsResponse>

    //Todo 3: create get method for getting articles by category
    @GET("top-headlines")
    fun getArticlesByCategories(@Query("category") category:String,@Query("apiKey")apiKey: String):Call<TopNewsResponse>
}