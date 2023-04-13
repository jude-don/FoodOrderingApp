package com.example.foodorderingapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodorderingapp.domain.Restaurant

@Composable
fun MainDetailsScreen(state: Restaurant?){
//    val viewModel:RestaurantDetailsViewModel = viewModel()
//    val item = viewModel.state
    if (state != null){
       Column(
           horizontalAlignment = Alignment.CenterHorizontally,
           modifier = Modifier
               .fillMaxSize()
               .padding(16.dp)
       ) {
           RestaurantIcon(
               icon = Icons.Filled.Place,
               modifier = Modifier.padding(
                   vertical = 32.dp
               )
           )
           RestaurantDetails(
               title = state.title,
               description = state.description,
               modifier = Modifier.padding(bottom = 32.dp),
               Alignment.CenterHorizontally
           )
           Text(text = "More info coming soon")


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
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        horizontalAlignment = horizontalAlignment,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6
        )
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Justify
            )
        }
    }
}

