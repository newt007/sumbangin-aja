package com.bintangpoetra.sumbanginaja.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.food.FoodUseCase
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import kotlinx.coroutines.launch

class HomeViewModel(
    private val foodUseCase: FoodUseCase
) : ViewModel(){

    val foodResult: LiveData<ApiResponse<List<Food>>> by lazy { _foodResult }
    private val _foodResult = MutableLiveData<ApiResponse<List<Food>>>()

    fun getFoods() {
        viewModelScope.launch{
            foodUseCase.fetchFood().collect {
                _foodResult.postValue(it)
            }
        }
    }

}