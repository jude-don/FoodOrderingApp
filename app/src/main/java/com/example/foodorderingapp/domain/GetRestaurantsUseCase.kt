package com.example.foodorderingapp.domain

import com.example.foodorderingapp.data.RestaurantsRepository
import javax.inject.Inject

class GetRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository
) {


    suspend operator fun invoke():List<Restaurant>{
        return repository.getAllRestaurants().sortedBy { it.title }
    }
}

/** We used the operator keyword since Kotlin allows us to define an invoke
 * operator on a class so we can call it on any instances of the class without
 * a method name. for example
 *  val useCase = GetRestaurantsUseCase()
 *  val result = useCase()
 * */