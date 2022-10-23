package com.bintangpoetra.sumbanginaja.presentation.region.province

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bintangpoetra.sumbanginaja.domain.region.RegionUseCase
import com.bintangpoetra.sumbanginaja.domain.region.model.Region
import kotlinx.coroutines.flow.Flow

class ProvinceViewModel(
    private val useCase: RegionUseCase
) : ViewModel() {

    val provincesResult: Flow<PagingData<Region>> = useCase.fetchProvinces().cachedIn(viewModelScope)
}