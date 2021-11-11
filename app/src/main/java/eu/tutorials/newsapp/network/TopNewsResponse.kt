package eu.tutorials.newsapp.network

//Todo 2: create Top news the base response
data class TopNewsResponse(val status : String? = null,
                           val totalResults : Int? = null,
                           val articles : List<TopNewsArticle>? = null)
