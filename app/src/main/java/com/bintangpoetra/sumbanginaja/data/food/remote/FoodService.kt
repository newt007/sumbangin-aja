package com.bintangpoetra.sumbanginaja.data.food.remote

import com.bintangpoetra.sumbanginaja.data.food.model.FoodItem
import com.bintangpoetra.sumbanginaja.data.lib.BaseResponse
import retrofit2.http.*

interface FoodService {

    @GET("foods")
    suspend fun fetchFood(): BaseResponse<List<FoodItem>>

    @GET("foods/{id}/show")
    suspend fun fetchFoodDetail(
        @Path("id") id: Int
    ): BaseResponse<FoodItem>

}