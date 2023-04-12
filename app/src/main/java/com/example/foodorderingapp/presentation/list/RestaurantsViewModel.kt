package com.example.foodorderingapp

import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderingapp.data.RestaurantsRepository
import com.example.foodorderingapp.domain.GetRestaurantsUseCase
import com.example.foodorderingapp.domain.Restaurant
import com.example.foodorderingapp.presentation.list.RestaurantsScreenState
import kotlinx.coroutines.*

sealed interface RestaurantState {
    data class Success(val listOfRestaurants: List<Restaurant>) : RestaurantState
    object Error : RestaurantState
    object Loading : RestaurantState
}

const val TAG = "MainActivity"

class RestaurantsViewModel(private val stateHandle: SavedStateHandle):ViewModel() {

   private var _state by mutableStateOf(
        RestaurantsScreenState(
            restaurant = listOf(),
            isLoading = true
        )
    )
    val state: RestaurantsScreenState
        get() = _state

    //Repository Instance
    private val repository = RestaurantsRepository()

    //GetRestaurantUseCase class
    private val getRestaurantsUseCase = GetRestaurantsUseCase()


    /** We have added an init block to instantiate the Retrofit builder
    object. This was done to intialize the retrofit builder object when the viewModel is intialized.*/
    init {
        getRestaurants()
    }

    /** We need to define a Coroutine error handler so it will throw all exceptions for us in the scope*/
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }



    /**Now we can execute requests - in our case, the requests to get the list of restaurants.*/
   private fun getRestaurants(){
        viewModelScope.launch {
            val restaurants = getRestaurantsUseCase()
            restaurants?.let {
                _state = state.copy(
                    restaurant = it,
                    isLoading = false
                )
            }
        }
    }


    fun toggleFavorite(id:Int){
//        val restaurants = _state.toMutableStateList()
//        val itemIndex = restaurants.indexOfFirst { it.id == id }
//        val item = restaurants[itemIndex]
//        restaurants[itemIndex] = item.copy(isFavorite = !item.isFavorite)
//        storeSelection(restaurants[itemIndex])
//        _state = restaurants
    }


    private fun storeSelection(item: Restaurant) {
        val savedToggled = stateHandle
            .get<List<Int>?>(FAVORITES)
            .orEmpty().toMutableStateList()
        if (item.isFavorite) savedToggled.add(item.id)
        else savedToggled.remove(item.id)
        stateHandle[FAVORITES] = savedToggled
    }

    private fun List<Restaurant>.restoreSelections(): List<Restaurant> {
        stateHandle.get<List<Int>?>(FAVORITES)?.let { selectedIds ->
            val restaurantsMap = this.associateBy { it.id }
            selectedIds.forEach { id ->
                restaurantsMap[id]?.isFavorite = true
            }
            return restaurantsMap.values.toList()
        }
        return this
    }

    companion object {
        const val FAVORITES = "favorites"
    }


}


