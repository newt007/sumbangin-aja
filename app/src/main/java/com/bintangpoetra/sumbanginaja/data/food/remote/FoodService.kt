package com.bintangpoetra.sumbanginaja.data.food.remote

import com.bintangpoetra.sumbanginaja.data.food.model.FoodItem
import com.bintangpoetra.sumbanginaja.data.lib.BaseResponse
import retrofit2.http.*

interface FoodService {

    @GET("foods")
    suspend fun fetchFood(
        @Header("accept") accept: String,
        @Header("Authorization") token: String): BaseResponse<List<FoodItem>>

    @GET("foods/{id}/show")
    suspend fun fetchFoodDetail(
        @Path("id") id: Int,
        @Header("accept") accept: String,
        @Header("Authorization") token: String): BaseResponse<FoodItem>

}