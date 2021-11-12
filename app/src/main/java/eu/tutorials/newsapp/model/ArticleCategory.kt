package eu.tutorials.newsapp.model

import eu.tutorials.newsapp.model.ArticleCategory.*

//Todo 2: create an enum class for the categories
enum class ArticleCategory(val categoryName:String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology")
}
//Todo 3: create a list for the category
fun getAllArticleCategory():List<ArticleCategory>{
    return listOf(BUSINESS, ENTERTAINMENT,GENERAL,HEALTH,SCIENCE,SPORTS,TECHNOLOGY)
}

//Todo 4: create a method to return a category by its value
fun getArticleCategory(category: String):ArticleCategory?{
    val map= values().associateBy(ArticleCategory::categoryName)
    return map[category]
}