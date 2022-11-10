package com.bintangpoetra.sumbanginaja.data.food.remote

import com.bintangpoetra.sumbanginaja.data.food.model.FoodItem
import com.bintangpoetra.sumbanginaja.data.lib.AlternateBaseResponse
import com.bintangpoetra.sumbanginaja.data.lib.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface FoodService {

    @GET("foods")
    suspend fun fetchFood(
    ): BaseResponse<List<FoodItem>>

    @GET("foods/{id}/show")
    suspend fun fetchFoodDetail(
        @Path("id") id: Int,
    ): BaseResponse<FoodItem>


    @Multipart
    @POST("food/create")
    suspend fun submitCreateFood(
        @Part images: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("descriptions") descriptions: RequestBody,
        @Part("payback_time") paybackTime: RequestBody,
        @Part("province_id") provinceId: RequestBody,
        @Part("city_id") cityId: RequestBody,
        @Part("address") address: RequestBody,
        @Part("latitude") latitude: RequestBody?,
        @Part("longitude") longitude: RequestBody?,
    ): BaseResponse<Nothing>


    @GET("food/{id}")
    suspend fun fetchFoodByUserId(
        @Path("id") id: String,
    ): BaseResponse<List<FoodItem>>

    @POST("scan")
    @FormUrlEncoded
    suspend fun scanningFood(
        @Field("barcode") barcode: String,
        @Field("type") type: String,
        @Field("qty") qty: String,
    ): AlternateBaseResponse

}