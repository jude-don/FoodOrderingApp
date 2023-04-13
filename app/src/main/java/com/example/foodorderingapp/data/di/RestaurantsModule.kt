package com.example.foodorderingapp.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodorderingapp.BASE_URL
import com.example.foodorderingapp.RestaurantApi
import com.example.foodorderingapp.RestaurantsApiService
import com.example.foodorderingapp.data.local.RestaurantsDao
import com.example.foodorderingapp.data.local.RestaurantsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RestaurantsModule {

    //Provide function for Dao object
    @Provides
    fun provideRoomDao(database: RestaurantsDb): RestaurantsDao {
        return database.dao
    }

    //Provide function for Dao object
    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext appContext:Context
    ):RestaurantsDb{
        return Room.databaseBuilder(
            appContext,
            RestaurantsDb::class.java,
            "restaurants_database"
        ).fallbackToDestructiveMigration().build()
    }

    //Provide function for Retrofit Instance
    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    //Provide function for creating an Api object
    @Provides
    fun provideRetrofitApi(retrofit: Retrofit):RestaurantsApiService{
        return retrofit.create(RestaurantsApiService::class.java)
    }
}