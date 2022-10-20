package com.bintangpoetra.sumbanginaja.data.food

import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.auth.model.User
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import kotlinx.coroutines.flow.Flow

interface FoodRepository {

    fun fetchFood(): Flow<ApiResponse<List<Food>>>

    fun fetchFoodDetail(id: Int): Flow<ApiResponse<Food>>

}