package com.example.foodorderingapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodorderingapp.domain.Restaurant

@Composable
fun RestaurantsScreen(navController: NavController,onItemClick:(id:Int)->Unit) {
    val viewModel:RestaurantsViewModel = viewModel()
    val state = viewModel.state
//    var state by remember {
//        mutableStateOf(viewModel.getRestaurants())
//    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 8.dp
            )
        ) {
            items(state.restaurant) { restaurant ->
                RestaurantItem(item = restaurant,
                    onFavoriteClick = { id ->
                        viewModel.toggleFavorite(id)
                    },
                    onItemClick = { id -> onItemClick(id) }
                )
            }
        }
        if (state.isLoading)
            CircularProgressIndicator(
                color = Color.Red
            )
    }
}

@Composable
fun RestaurantItem(
    item: Restaurant,
    onFavoriteClick: (id:Int) -> Unit,
    onItemClick:(id:Int)->Unit
){

    val icon = if (item.isFavorite)
        Icons.Filled.Favorite
    else
        Icons.Filled.FavoriteBorder
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onItemClick(item.id)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            RestaurantIcon(Icons.Filled.Place, Modifier.weight(0.15f))
            RestaurantDetails(item.title, item.description, Modifier.weight(0.7f))
            RestaurantIcon(icon = icon, modifier = Modifier.weight(0.15f)){
                onFavoriteClick(item.id)
            }
        }
    }
}

@Composable
private fun RestaurantIcon(
                icon: ImageVector,
                modifier: Modifier,
                onClick: () -> Unit ={ },

) {
    Image(
        imageVector = icon,
        contentDescription = "Restaurant icon",
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() }
    )
}

@Composable
private fun RestaurantDetails(
    title: String,
    description: String,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6
        )
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

//@Composable
//private fun FavoriteIcon(
//    icon: ImageVector,
//    modifier: Modifier,
//    favoriteState:Boolean,
//    onClick:()-> Unit
//){
//    Image(
//        imageVector = icon,
//        contentDescription = "Favorite restaurant icon",
//        modifier = modifier
//            .padding(8.dp)
//            .clickable { onClick() },
//        colorFilter =
//        if (favoriteState)
//            ColorFilter.tint(Color.Red)
//        else
//            ColorFilter.tint(Color.Black)
//    )
//}
