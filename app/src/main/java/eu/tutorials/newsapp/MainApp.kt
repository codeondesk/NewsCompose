package eu.tutorials.newsapp

import android.app.Application
import eu.tutorials.newsapp.data.network.Api
import eu.tutorials.newsapp.data.network.NewsManager
import eu.tutorials.newsapp.data.Repository

//Todo 7: create a subclass of the application class
class MainApp:Application() {

    //Todo 8: initialize the NewsManager
    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    //Todo 9: initialize the repository with the news manager
    val repository by lazy {
        Repository(manager = manager)
    }


}