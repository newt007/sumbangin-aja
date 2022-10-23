package com.bintangpoetra.sumbanginaja.domain.food

import android.location.Location
import com.bintangpoetra.sumbanginaja.data.food.FoodRepository
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class FoodInteractor(
    private val repository: FoodRepository
) : FoodUseCase {

    override fun fetchFood(): Flow<ApiResponse<List<Food>>> {
        return repository.fetchFood().flowOn(Dispatchers.IO)
    }

    override fun fetchFoodDetail(id: Int): Flow<ApiResponse<Food>> {
        return repository.fetchFoodDetail(id).flowOn(Dispatchers.IO)
    }

    override fun createFood(
        images: File,
        name: String,
        description: String,
        paybackTime: String,
        provinceId: Int,
        cityId: Int,
        address: String,
        location: Location?
    ): Flow<ApiResponse<String>> {
        return repository.createFood(
            images, name, description, paybackTime, provinceId, cityId, address, location
        )
    }

    override fun scanningFood(
        barcode: String,
        type: String,
        qty: String
    ): Flow<ApiResponse<String>> {
        return repository.scanningFood(barcode, type, qty).flowOn(Dispatchers.IO)
    }

    override fun fetchFoodByUserId(): Flow<ApiResponse<List<Food>>> {
        return repository.fetchFoodByUserId().flowOn(Dispatchers.IO)
    }

}