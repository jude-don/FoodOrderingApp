package com.example.foodorderingapp

import com.example.foodorderingapp.data.remote.RemoteRestaurant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val  BASE_URL = "https://restaurantdb-59e77-default-rtdb.firebaseio.com/"

/** The retrofit was instantied  by assigning a variable to the Retrofit.Builder accessor.
        * It was specified with the following.
        * - A GsonConverterFactory to explicitly tell Retrofit that we want the Json to be deserialized
        *   with the Gson converter, following the @Serialized annotations we specified in the Restaurant class.
        * - The baseUrl for all the requests that are to be executed.
        * - */
private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(
        GsonConverterFactory.create()
    )
    .build()


interface RestaurantsApiService {
    @GET ("restaurants.json")
    suspend fun getRestuarants() : List<RemoteRestaurant>

    @GET("restaurants.json?orderBy=\"r_id\"")
    suspend fun getRestaurants2(
        @Query("equalTo") id: Int ):Map<String, RemoteRestaurant >
}




/** We call the .create() on the previously obtained Retrofit object and passed our interface with the
*  desired requests: RestaurantsApiService. Behind the scenes, Retrofit creates a concrete implementation
*  of our interface that will handle all the networking logic, without us having to worry about it. We store
*  this instance from Retrofit inside our restInterface variable. */

object RestaurantApi{

    /** the restInterface variable of type RestaurantsApiService
    (the interface we created). We will call upon this to execute
    the desired network requests. At this point, the restInterface
    variable holds no value.*/
    val restInterface:RestaurantsApiService by lazy {
        retrofit.create(RestaurantsApiService::class.java)
    }
    /** Remember "lazy initialization" is when object creation is purposely delayed,
    until you actually need that object, to avoid unnecessary computation or use
    of other computing resources. Kotlin has first-class support for lazy instantiation. */

}
