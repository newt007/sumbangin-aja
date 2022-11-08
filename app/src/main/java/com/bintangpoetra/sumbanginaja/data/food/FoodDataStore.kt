package com.bintangpoetra.sumbanginaja.data.food

import android.location.Location
import com.bintangpoetra.sumbanginaja.data.food.remote.FoodService
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.food.mapper.toDomain
import com.bintangpoetra.sumbanginaja.domain.food.mapper.toListDomain
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import com.bintangpoetra.sumbanginaja.utils.ext.toBearer
import com.bintangpoetra.sumbanginaja.utils.ext.toMultipart
import com.bintangpoetra.sumbanginaja.utils.ext.toRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class FoodDataStore(
    private val api: FoodService,
    private val pref: PreferenceManager
) : FoodRepository {

    override fun fetchFood(): Flow<ApiResponse<List<Food>>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.fetchFood()

//            if (response.status) {
//                val foodData = response.data.toListDomain()
//                emit(ApiResponse.Success(foodData))
//            } else {
//                emit(ApiResponse.Error(response.message))
//            }
            val foodData = response.data.toListDomain()
            emit(ApiResponse.Success(foodData))
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.message ?: "Unknown Error"))
            ex.printStackTrace()
        }
    }

    override fun fetchFoodDetail(id: Int): Flow<ApiResponse<Food>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.fetchFoodDetail(id)

//            if (response.status) {
//                val foodData = response.data.toListDomain()
//                emit(ApiResponse.Success(foodData))
//            } else {
//                emit(ApiResponse.Error(response.message))
//            }
            val foodData = response.data.toDomain()
            emit(ApiResponse.Success(foodData))
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.message ?: "Unknown Error"))
            ex.printStackTrace()
        }
    }

    override fun fetchFoodByUserId(): Flow<ApiResponse<List<Food>>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.fetchFoodByUserId(pref.getUserId.toString())

            val foodData = response.data.toListDomain()
            if (foodData.isEmpty()) {
                emit(ApiResponse.Empty)
            } else {
                emit(ApiResponse.Success(foodData))
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.message ?: "Unknown Error"))
            ex.printStackTrace()
        }
    }

    override fun scanningFood(
        barcode: String,
        type: String,
        qty: String
    ): Flow<ApiResponse<String>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.scanningFood(barcode, type, qty)
            val message = response.message

            if (response.status) {
                emit(ApiResponse.Success(message))
            } else {
                emit(ApiResponse.Error(message))
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.message.toString()))
            ex.printStackTrace()
        }
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
    ): Flow<ApiResponse<String>> = flow {
        try {
            emit(ApiResponse.Loading)

            val mImages = images.toMultipart("images")
            val mName = name.toRequestBody()
            val mDescription = description.toRequestBody()
            val mPaybackTime = paybackTime.toRequestBody()
            val mProvinceId = provinceId.toRequestBody()
            val mCityId = cityId.toRequestBody()
            val mAddress = address.toRequestBody()
            val mLatitude = location?.latitude?.toRequestBody()
            val mLongitude = location?.longitude?.toRequestBody()

            val response = api.submitCreateFood(
                images = mImages,
                name = mName,
                descriptions = mDescription,
                paybackTime = mPaybackTime,
                provinceId = mProvinceId,
                cityId = mCityId,
                address = mAddress,
                latitude = mLatitude,
                longitude = mLongitude,
            )

            if (response.status || response.success){
                emit(ApiResponse.Success(response.message))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (ex: Exception){
            emit(ApiResponse.Error(ex.message ?: "Unknown Error"))
            ex.printStackTrace()
        }
    }

}