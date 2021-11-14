package eu.tutorials.newsapp.network

import androidx.compose.runtime.*
import eu.tutorials.newsapp.model.ArticleCategory
import eu.tutorials.newsapp.model.getArticleCategory
import eu.tutorials.newsapp.network.models.TopNewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager {

    private val _newsResponse =
        mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    private val _getArticleByCategory =
        mutableStateOf(TopNewsResponse())
    val getArticleByCategory:State<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
        }

    val sourceName = mutableStateOf("abc-news")

    private val _getArticleBySource =  mutableStateOf(TopNewsResponse())
    val getArticleBySource :State<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }
            val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)


    init {
        getArticles()
    }

    val query = mutableStateOf("")

    private fun getArticles(){
        val service = Api.retrofitService.getTopArticles("us")
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful){
                    _newsResponse.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {

            }

        })
    }

    fun getArticlesByCategory(category: String){
        val client = Api.retrofitService.getArticlesByCategories(category)
        client.enqueue(object :Callback<TopNewsResponse>{
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful){
                    _getArticleByCategory.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {

            }

        })
    }
    fun onSelectedCategoryChanged(category:String){
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }

    fun getArticleBySource(){
        val client = Api.retrofitService.getArticlesBySouurces(sourceName.value)
        client.enqueue(object :Callback<TopNewsResponse>{
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful){
                    _getArticleBySource.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {

            }

        })
    }
}