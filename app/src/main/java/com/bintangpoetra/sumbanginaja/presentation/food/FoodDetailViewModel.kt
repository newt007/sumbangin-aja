package com.bintangpoetra.sumbanginaja.presentation.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.food.FoodUseCase
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import kotlinx.coroutines.launch

class FoodDetailViewModel (
    private val foodUseCase: FoodUseCase
): ViewModel() {

    val foodDetailResult: LiveData<ApiResponse<Food>> by lazy { _foodDetailResult }
    private val _foodDetailResult = MutableLiveData<ApiResponse<Food>>()

    fun getFoodDetail(id: Int) {
        viewModelScope.launch {
            foodUseCase.fetchFoodDetail(id).collect{
                _foodDetailResult.postValue(it)
            }
        }
    }

}