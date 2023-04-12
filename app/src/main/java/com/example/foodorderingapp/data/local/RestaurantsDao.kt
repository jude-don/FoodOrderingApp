package com.example.foodorderingapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodorderingapp.data.local.LocalRestaurant

@Dao  /** Just as Retrofit had an interface for the HTTP methods, DAO has this interface to define methods*/
interface RestaurantsDao {  /** This is the Data Access Object (DAO)*/
    @Query("SELECT * FROM restaurants")
    suspend fun getAll(): List<LocalRestaurant> /** getAll() is a query statement that returns the restaurants that
                                               were previously cached inside the database. Since we need to perform
                                               a SQL query when calling this method, we've marked it with the @Query annotation
                                               and specified that we want all the restaurants (by adding *) from the restaurants
                                               table defined in the Restaurant entity data class. */


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(restaurant: List<LocalRestaurant>) /** addAll() is an insert statement that caches the received restaurants inside
                                                         the database. To mark this as a SQL insert statement, we've added the @Insert
                                                         annotation. However, if the restaurants being inserted are already present in
                                                         the database, we should replace the old ones with the new ones to refresh our
                                                         cache. We instructed Room to do so by passing the OnConflictStrategy.REPLACE
                                                         value into the @Insert annotation. */

}