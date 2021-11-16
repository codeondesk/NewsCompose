package eu.tutorials.newsapp.data

import eu.tutorials.newsapp.data.network.NewsManager

//Todo 5: create a data package, create a Repository class within it, and move the network package into it
class Repository(val manager: NewsManager) {

    //Todo 6: create methods and return getArticles and getArticlesByCategory
    suspend fun getArticles() = manager.getArticles("us")

    suspend fun getArticleByCategory(category:String) = manager.getArticlesByCategory(category)
}