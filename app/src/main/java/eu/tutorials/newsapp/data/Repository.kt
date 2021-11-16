package eu.tutorials.newsapp.data

import eu.tutorials.newsapp.data.network.NewsManager

class Repository(private val manager: NewsManager) {

    suspend fun getArticles() = manager.getArticles("us")

    suspend fun getArticleByCategory(category:String) = manager.getArticlesByCategory(category)


   //Todo 3: create methods to return getArticlesBySource and getSearchedArticles
    suspend fun getArticlesBySource(source:String) = manager.getArticleBySource(source = source)

    suspend fun getSearchedArticles(query:String) = manager.getSearchedArticles(query = query)

}