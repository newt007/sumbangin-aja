package com.bintangpoetra.sumbanginaja.data.food.remote

import com.bintangpoetra.sumbanginaja.data.food.model.FoodItem
import com.bintangpoetra.sumbanginaja.data.lib.BaseResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FoodService {

    @POST("foods")
    @FormUrlEncoded
    suspend fun fetchFood(): BaseResponse<List<FoodItem>>

}