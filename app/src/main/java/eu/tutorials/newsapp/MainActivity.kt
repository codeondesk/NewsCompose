package eu.tutorials.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import eu.tutorials.newsapp.ui.MainViewModel
import eu.tutorials.newsapp.ui.NewsApp
import eu.tutorials.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    //Todo 20: initialize the viewModel
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Todo 21 call getTopArticles method
        viewModel.getTopArticles()
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //Todo 22: pass in viewModel to NewsApp
                    NewsApp(viewModel)
                }
            }
        }
    }
}

//Todo 23: provide default viewModel to the preview
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsAppTheme {
        NewsApp(viewModel())
  }
}