package com.bintangpoetra.sumbanginaja.presentation.region.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bintangpoetra.sumbanginaja.domain.region.RegionUseCase
import com.bintangpoetra.sumbanginaja.domain.region.model.Region
import kotlinx.coroutines.flow.Flow

class CityViewModel (
    private val useCase: RegionUseCase
) : ViewModel() {

    fun citiesResult(id: Int): Flow<PagingData<Region>> = useCase.fetchCities(id).cachedIn(viewModelScope)
}