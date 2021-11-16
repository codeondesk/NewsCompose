package eu.tutorials.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.newsapp.MainApp
import eu.tutorials.newsapp.data.models.TopNewsResponse
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
     * set the laoding value to true before launch and to false
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


}