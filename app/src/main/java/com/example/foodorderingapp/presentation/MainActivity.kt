package com.example.foodorderingapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.foodorderingapp.*
import com.example.foodorderingapp.ui.theme.FoodOrderingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodOrderingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RestaurantsApp()
                }
            }
        }
    }
}

@Composable
private fun RestaurantsApp(){
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = "restaurants"
    ){

        composable(route = "restaurants"){
            val viewModel:RestaurantsViewModel = hiltViewModel()
            RestaurantsScreen(state = viewModel.state){
                    id -> navController.navigate("restaurants/$id")
            }

        }


        composable(
            route = "restaurants/{restaurant_id}",
            arguments = listOf(navArgument("restaurant_id"){
                type = NavType.IntType
            }
            ),
            deepLinks = listOf(navDeepLink {
                uriPattern = "www.restaurantsapp.details.com/{restaurant_id}"
            })
        ){
            val detailsViewModel:RestaurantDetailsViewModel = hiltViewModel()
            val id = it.arguments?.getInt("restaurant_id")
            MainDetailsScreen(state = detailsViewModel.state)
        }
    }

}