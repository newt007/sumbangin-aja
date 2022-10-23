package com.bintangpoetra.sumbanginaja.presentation.food.add

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.food.FoodUseCase
import kotlinx.coroutines.launch
import java.io.File

class AddFoodViewModel(
    private val foodUseCase: FoodUseCase
) : ViewModel() {

    val addFoodResult: LiveData<ApiResponse<String>> by lazy { _addFoodResult }
    private val _addFoodResult = MutableLiveData<ApiResponse<String>>()

    fun submitFood(
        images: File,
        name: String,
        description: String,
        paybackTime: String,
        provinceId: Int,
        cityId: Int,
        address: String,
        location: Location?
    ) {
        viewModelScope.launch {
            foodUseCase.createFood(
                images,
                name,
                description,
                paybackTime,
                provinceId,
                cityId,
                address,
                location
            ).collect {
                _addFoodResult.postValue(it)
            }
        }
    }

}