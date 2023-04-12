package com.example.foodorderingapp.data

import com.example.foodorderingapp.domain.Restaurant
import com.example.foodorderingapp.RestaurantApi
import com.example.foodorderingapp.RestaurantsApplication
import com.example.foodorderingapp.data.local.LocalRestaurant
import com.example.foodorderingapp.data.local.RestaurantsDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class RestaurantsRepository {

    //Database Variable
    private var restaurantsDao = RestaurantsDb.getDaoInstance(
        RestaurantsApplication.getAppContext()
    )

    suspend fun getAllRestaurants():List<Restaurant> {
        return withContext(Dispatchers.IO){

            try {
                refreshCache()
            }
            catch (e:Exception){
                when (e){
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {

                        if (restaurantsDao.getAll().isEmpty())
                            throw Exception(
                                "Something went wrong. " +
                                        "We have no data.")

                    }
                    else -> throw e
                }
            }
            return@withContext restaurantsDao.getAll().map {
                Restaurant(it.id, it.title, it.description, it.isFavorite)
            }

        }
    }


    private suspend fun refreshCache(){
        val remoteRestaurants = RestaurantApi.restInterface.getRestuarants()
        restaurantsDao.addAll(remoteRestaurants.map { LocalRestaurant(it.id,it.title,it.description,false) })  /** Here we are caching the restaurants retrofit gave us */
    }



}