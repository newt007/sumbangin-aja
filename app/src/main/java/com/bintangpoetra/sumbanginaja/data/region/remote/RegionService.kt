package com.bintangpoetra.sumbanginaja.data.region.remote

import com.bintangpoetra.sumbanginaja.data.food.model.FoodItem
import com.bintangpoetra.sumbanginaja.data.lib.BaseResponse
import com.bintangpoetra.sumbanginaja.data.region.model.RegionItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface RegionService {

    @GET("provinces")
    suspend fun fetchProvinces(): BaseResponse<List<RegionItem>>

    @GET("province/{id}")
    suspend fun fetchCities(
        @Path("id") id: Int
    ): BaseResponse<List<RegionItem>>

}