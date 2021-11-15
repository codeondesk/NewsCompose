package eu.tutorials.newsapp.network

import android.util.Log
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

    //Todo 5: create a query variable for the searched word
    val query = mutableStateOf("")

    //todo 8: create setter and getter
    private val _searchedNewsResponse =
        mutableStateOf(TopNewsResponse())
    val searchedNewsResponse:State<TopNewsResponse>
        @Composable get() = remember {
            _searchedNewsResponse
        }

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
        val client = Api.retrofitService.getArticlesBySources(sourceName.value)
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

    //Todo 9:process the search request and set response to the value holder
    fun getSearchedArticles(query: String){
        val client = Api.retrofitService.searchArticles(query)
        client.enqueue(object :Callback<TopNewsResponse>{
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful){
                    _searchedNewsResponse.value = response.body()!!
                    Log.d("search","${_searchedNewsResponse.value}")
                }else{
                    Log.d("search","${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("searcherror","${t.printStackTrace()}")
            }

        })
    }
}