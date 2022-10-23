package com.bintangpoetra.sumbanginaja.data.food

import android.location.Location
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FoodRepository {

    fun fetchFood(): Flow<ApiResponse<List<Food>>>

    fun fetchFoodDetail(id: Int): Flow<ApiResponse<Food>>

    fun createFood(
        images: File,
        name: String,
        description: String,
        paybackTime: String,
        provinceId: Int,
        cityId: Int,
        address: String,
        location: Location?
    ): Flow<ApiResponse<String>>

}