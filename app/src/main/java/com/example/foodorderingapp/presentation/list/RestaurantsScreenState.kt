package com.example.foodorderingapp.presentation.list

import com.example.foodorderingapp.domain.Restaurant

data class RestaurantsScreenState(
    val restaurant: List<Restaurant>,
    val isLoading:Boolean,
    val error: String? = null
)
