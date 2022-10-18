package com.bintangpoetra.sumbanginaja.domain.food

import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.auth.model.User
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import kotlinx.coroutines.flow.Flow

interface FoodUseCase {

    fun fetchFood(): Flow<ApiResponse<List<Food>>>

}