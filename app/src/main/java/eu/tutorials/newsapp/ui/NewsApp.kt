package eu.tutorials.newsapp.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.newsapp.BottomMenuScreen
import eu.tutorials.newsapp.components.BottomMenu
import eu.tutorials.newsapp.data.models.TopNewsArticle
import eu.tutorials.newsapp.data.network.Api
import eu.tutorials.newsapp.data.network.NewsManager
import eu.tutorials.newsapp.ui.screen.Categories
import eu.tutorials.newsapp.ui.screen.DetailScreen
import eu.tutorials.newsapp.ui.screen.Sources
import eu.tutorials.newsapp.ui.screen.TopNews

@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()

    MainScreen(navController = navController,scrollState,mainViewModel)
}

@Composable
fun MainScreen(navController: NavHostController,scrollState: ScrollState,mainViewModel: MainViewModel) {
    Scaffold(bottomBar ={
        BottomMenu(navController = navController)
    }) {
        Navigation(navController =navController , scrollState =scrollState,paddingValues = it,viewModel = mainViewModel )
    }
}

@Composable
fun Navigation(navController:NavHostController, scrollState: ScrollState, paddingValues: PaddingValues, viewModel: MainViewModel) {
    val articles = mutableListOf(TopNewsArticle())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    val query by viewModel.query.collectAsState()
    //Todo 11 collect loading and error state from viewModel
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
    articles.addAll(topArticles ?: listOf())
    NavHost(navController = navController, startDestination =BottomMenuScreen.TopNews.route,modifier = Modifier.padding(paddingValues)) {
       val queryState =
            mutableStateOf(query)
        //Todo 12 set to a mutable state
        val isLoading = mutableStateOf(loading)
        val isError = mutableStateOf(error)
                //Todo 13: pass in the state values as argument yo bottomNavigation
        bottomNavigation(navController = navController, articles,viewModel = viewModel,query = queryState,
        isError = isError,isLoading = isLoading)
        composable("Detail/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                if (queryState.value != "") {
                    articles.clear()
                    articles.addAll(viewModel.searchedNewsResponse.collectAsState().value.articles?: listOf())
                }else{
                    articles.clear()
                    articles.addAll(topArticles?: listOf())
                }
                val article = articles[index]
                DetailScreen(article, scrollState, navController)
            }
        }
    }
}

//Todo 9: create the loading and error state parameter
fun NavGraphBuilder.bottomNavigation(navController: NavController, articles:List<TopNewsArticle>, query: MutableState<String>,
                                     viewModel: MainViewModel,
                                     isLoading:MutableState<Boolean>,isError:MutableState<Boolean>
) {

    composable(BottomMenuScreen.TopNews.route) {
        //Todo 10: pass in the loading and error value as argument
       TopNews(navController = navController,articles,query,viewModel = viewModel,isLoading = isLoading
       ,isError = isError)
        }
    composable(BottomMenuScreen.Categories.route) {
        viewModel.getArticlesByCategory("business")
        viewModel.onSelectedCategoryChanged("business")
        Categories(viewModel = viewModel,onFetchCategory = {
            viewModel.onSelectedCategoryChanged(it)
        viewModel.getArticlesByCategory(it)
        },isError = isError,isLoading = isLoading)
    }
    composable(BottomMenuScreen.Sources.route) {
        //Todo 16: pass in the loading and error state argument already in the bottom navigation paranthesis
        Sources(viewModel = viewModel,isLoading = isLoading,isError = isError)
    }
}