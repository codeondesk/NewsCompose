package eu.tutorials.newsapp

import android.app.Application
import eu.tutorials.newsapp.data.network.Api
import eu.tutorials.newsapp.data.network.NewsManager
import eu.tutorials.newsapp.data.Repository

class MainApp:Application() {

    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    val repository by lazy {
        Repository(manager = manager)
    }


}