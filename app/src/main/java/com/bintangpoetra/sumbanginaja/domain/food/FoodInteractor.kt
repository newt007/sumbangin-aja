package com.bintangpoetra.sumbanginaja.domain.food

import com.bintangpoetra.sumbanginaja.data.food.FoodRepository
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class FoodInteractor(
    private val repository: FoodRepository
) : FoodUseCase {

    override fun fetchFood(): Flow<ApiResponse<List<Food>>> {
        return repository.fetchFood().flowOn(Dispatchers.IO)
    }
}