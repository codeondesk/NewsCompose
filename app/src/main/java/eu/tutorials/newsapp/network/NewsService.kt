package eu.tutorials.newsapp.network

import eu.tutorials.newsapp.network.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//Todo 5 create request interfaces
interface NewsService {
    //Todo 6: create the get method for topNews
    @GET("top-headlines")
    fun getTopArticles(@Query("country") country:String, @Query("apiKey")apiKey:String): Call<TopNewsResponse>
}