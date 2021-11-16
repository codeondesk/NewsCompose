package eu.tutorials.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.newsapp.MainApp
import eu.tutorials.newsapp.data.models.TopNewsResponse
import eu.tutorials.newsapp.model.ArticleCategory
import eu.tutorials.newsapp.model.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//Todo 11: create the application class and extend AndroidViewmodel
class MainViewModel(application: Application) : AndroidViewModel(application) {

    //Todo 12: get repository from the MainApp
    private val repository = getApplication<MainApp>().repository

    //Todo 13: create a setter and getter for the newsReponse
    private val _newsResponse = MutableStateFlow(TopNewsResponse())
    val newsResponse: StateFlow<TopNewsResponse>
    get() = _newsResponse

    //Todo 14: create a variable to keep track of the loading state
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    /** Todo 15: create a method to launch getArticles
     * set the loading value to true before launch and to false
     * after the coroutine
     *
      */
    fun getTopArticles(){
        _isLoading.value  = true
        viewModelScope.launch(Dispatchers.IO) {
            _newsResponse.value = repository.getArticles()
        }
        _isLoading.value = false
    }

    //Todo 26: create a setter and getter for selectedCategory tab
    private val _selectedCategory:MutableStateFlow<ArticleCategory?> = MutableStateFlow(null)
       val  selectedCategory:StateFlow<ArticleCategory?>
        get() = _selectedCategory

    //Todo 24: move the setter and getter for Article by Category from NewsManager and use MutableStateFlow
    private val _getArticleByCategory =
        MutableStateFlow(TopNewsResponse())
    val getArticleByCategory: StateFlow<TopNewsResponse>
       get() = _getArticleByCategory

    //Todo 25: create a method and launch the getArticleByCategory
    fun getArticlesByCategory(category:String){
        _isLoading.value  = true
        viewModelScope.launch(Dispatchers.IO) {
            _getArticleByCategory.value = repository.getArticleByCategory(category =category )
        }
        _isLoading.value = false
    }

    //Todo 27: move the method for changing the category from NewsManager
    fun onSelectedCategoryChanged(category:String){
        val newCategory = getArticleCategory(category = category)
        _selectedCategory.value = newCategory
    }

}