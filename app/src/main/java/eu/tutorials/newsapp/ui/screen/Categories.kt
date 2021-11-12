package eu.tutorials.newsapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import eu.tutorials.newsapp.R

@Composable
fun Categories() {
    Text(text = "Categories Screen")
}

/**Todo 1: create categories tab with 3 parameters
 * @param category keeps track of the selected category
 * @param isSelected is used to change the tab color depending on the value
 * @param onFetchCategory is the action performed when a tab is selected.
 */
@Composable
fun CategoryTab(category: String,
                isSelected: Boolean = false,
                onFetchCategory: (String) -> Unit) {
    val background = if(isSelected) colorResource(id = R.color.purple_200) else colorResource(id =R.color.purple_700)
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
        )

    }
}