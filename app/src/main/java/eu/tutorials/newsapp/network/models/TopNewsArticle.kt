package eu.tutorials.newsapp.network.models


//Todo 3: create top news article model
data class TopNewsArticle(
    val source : Source? = null,
    val author : String? = null,
    val title : String? = null,
    val description : String? = null,
    val url : String? = null,
    val urlToImage : String? = null,
    val publishedAt : String? = null,
    val content : String? = null
)
