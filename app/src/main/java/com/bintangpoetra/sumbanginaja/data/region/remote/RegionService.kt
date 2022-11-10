package com.bintangpoetra.sumbanginaja.data.region.remote

import com.bintangpoetra.sumbanginaja.data.lib.BaseResponse
import com.bintangpoetra.sumbanginaja.data.region.model.RegionItem
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RegionService {

    @GET("provinces")
    suspend fun fetchProvinces(
        ): BaseResponse<List<RegionItem>>

    @GET("province/{id}")
    suspend fun fetchCities(
        @Path("id") id: Int
    ): BaseResponse<List<RegionItem>>

}