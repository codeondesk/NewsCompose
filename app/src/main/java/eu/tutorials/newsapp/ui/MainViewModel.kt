package eu.tutorials.newsapp.ui

import android.accounts.NetworkErrorException
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.newsapp.MainApp
import eu.tutorials.newsapp.data.models.TopNewsResponse
import eu.tutorials.newsapp.model.ArticleCategory
import eu.tutorials.newsapp.model.getArticleCategory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.http2.ErrorCode

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = getApplication<MainApp>().repository

    private val _newsResponse = MutableStateFlow(TopNewsResponse())
    val newsResponse: StateFlow<TopNewsResponse>
        get() = _newsResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    //Todo 2: create the error state variable setter and getter
    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean>
        get() = _isError

    //Todo 3: create a coroutine exception handler and set isError to true

    val errorHandler = CoroutineExceptionHandler { _, error ->
        if (error is Exception) {
            _isError.value = true
        }
    }


    fun getTopArticles() {
        _isLoading.value = true
        //Todo 4 Since we are using coroutine for processing the request. coroutine exception handler is attached to the dispatcher
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _newsResponse.value = repository.getArticles()
            _isLoading.value = false
        }
    }

    private val _selectedCategory: MutableStateFlow<ArticleCategory?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<ArticleCategory?>
        get() = _selectedCategory

    private val _getArticleByCategory =
        MutableStateFlow(TopNewsResponse())
    val getArticleByCategory: StateFlow<TopNewsResponse>
        get() = _getArticleByCategory

    fun getArticlesByCategory(category: String) {
        _isLoading.value = true
        //Todo 5 Since we are using coroutine for processing the request. coroutine exception handler is attached to the dispatcher
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _getArticleByCategory.value = repository.getArticleByCategory(category = category)
            _isLoading.value = false
        }

    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category = category)
        _selectedCategory.value = newCategory
    }


    val sourceName = MutableStateFlow("engadget")

    private val _getArticleBySource = MutableStateFlow(TopNewsResponse())
    val getArticleBySource: StateFlow<TopNewsResponse>
        get() = _getArticleBySource

    val query = MutableStateFlow("")

    private val _searchedNewsResponse =
        MutableStateFlow(TopNewsResponse())
    val searchedNewsResponse: StateFlow<TopNewsResponse>
        get() = _searchedNewsResponse

    fun getArticleBySource() {
        _isLoading.value = true
        //Todo 6 Since we are using coroutine for processing the request. coroutine exception handler is attached to the dispatcher
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _getArticleBySource.value = repository.getArticlesBySource(sourceName.value)
            _isLoading.value = false
        }
    }

    fun getSearchedArticles(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchedNewsResponse.value = repository.getSearchedArticles(query)
        }
    }
}