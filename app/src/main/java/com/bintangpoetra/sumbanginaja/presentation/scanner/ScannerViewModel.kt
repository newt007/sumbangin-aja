package com.bintangpoetra.sumbanginaja.presentation.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.food.FoodUseCase
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val foodUseCase: FoodUseCase
): ViewModel() {

    val scanningFoodResult: LiveData<ApiResponse<String>> by lazy { _scanningFoodResult }
    private val _scanningFoodResult = MutableLiveData<ApiResponse<String>>()

    fun scanningFood(
        barcode: String,
        type: String,
        qty: String
    ) {
        viewModelScope.launch {
            foodUseCase.scanningFood(barcode, type, qty)
                .collect {
                    _scanningFoodResult.postValue(it)
                }
        }
    }

}