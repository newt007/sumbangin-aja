package com.bintangpoetra.sumbanginaja.data.food

import com.bintangpoetra.sumbanginaja.data.food.remote.FoodService
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.food.mapper.toDomain
import com.bintangpoetra.sumbanginaja.domain.food.mapper.toListDomain
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FoodDataStore(
    private val api: FoodService
): FoodRepository {

    override fun fetchFood(): Flow<ApiResponse<List<Food>>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.fetchFood("application/json", "Bearer 12|uENKa2lxwNuWZtBi883WDpkEJZXFcAfc7CbPzTwb")

//            if (response.status) {
//                val foodData = response.data.toListDomain()
//                emit(ApiResponse.Success(foodData))
//            } else {
//                emit(ApiResponse.Error(response.message))
//            }
            val foodData = response.data.toListDomain()
            emit(ApiResponse.Success(foodData))
        } catch (ex: Exception){
            emit(ApiResponse.Error(ex.message ?: "Unknown Error"))
            ex.printStackTrace()
        }
    }

    override fun fetchFoodDetail(id: Int): Flow<ApiResponse<Food>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.fetchFoodDetail(id,"application/json", "Bearer 12|uENKa2lxwNuWZtBi883WDpkEJZXFcAfc7CbPzTwb")

//            if (response.status) {
//                val foodData = response.data.toListDomain()
//                emit(ApiResponse.Success(foodData))
//            } else {
//                emit(ApiResponse.Error(response.message))
//            }
            val foodData = response.data.toDomain()
            emit(ApiResponse.Success(foodData))
        } catch (ex: Exception){
            emit(ApiResponse.Error(ex.message ?: "Unknown Error"))
            ex.printStackTrace()
        }
    }

}