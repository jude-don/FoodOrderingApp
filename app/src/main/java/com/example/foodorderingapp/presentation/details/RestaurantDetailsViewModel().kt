package com.example.foodorderingapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderingapp.data.local.LocalRestaurant
import com.example.foodorderingapp.data.local.RestaurantsDb
import com.example.foodorderingapp.domain.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantDetailsViewModel(
    private val stateHandle: SavedStateHandle
    ):ViewModel() {

    var state by mutableStateOf<Restaurant?>(null)
        private set


    private var restaurantsDao = RestaurantsDb.getDaoInstance(
        RestaurantsApplication.getAppContext()
    )



    init {
        val id = stateHandle.get<Int>("restaurant_id")?: 0
        getMyRestaurant(id)
    }
    private suspend fun getRemoteRestaurants(id:Int): Restaurant {
        return withContext(Dispatchers.IO){
            val responseMap = RestaurantApi.restInterface.getRestaurants2(id)
            return@withContext responseMap.values.first().let {
                Restaurant(
                    id = it.id,
                    title = it.title,
                    description = it.description
                )
            }
        }
    }
    private suspend fun refreshCache(){
        val restaurants = RestaurantApi.restInterface.getRestuarants()
        restaurantsDao.addAll(restaurants.map {
            LocalRestaurant(
                id = it.id,
                title = it.title,
                description = it.description
            )
        })  /** Here we are caching the restaurants retrofit gave us */
    }

    private fun getMyRestaurant(id:Int){
        viewModelScope.launch {
            try {
                    withContext(Dispatchers.IO){
                        for (data in restaurantsDao.getAll().map {
                            Restaurant(
                                id = it.id,
                                title = it.title,
                                description = it.description
                            )
                        }){
                            if (data.id == id){
                                state = data
                            }
                        }
                    }
                }
            catch (e:Exception){
                if (restaurantsDao.getAll().isEmpty()){
                    throw Exception(
                        "Something went wrong. " +
                                "We have no data. "+ e.message )
                }
            }

        }

    }
}