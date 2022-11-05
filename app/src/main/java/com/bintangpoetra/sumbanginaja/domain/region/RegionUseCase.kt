package com.bintangpoetra.sumbanginaja.domain.region

import androidx.paging.PagingData
import com.bintangpoetra.sumbanginaja.domain.region.model.Region
import kotlinx.coroutines.flow.Flow

interface RegionUseCase {
    fun fetchProvinces(): Flow<PagingData<Region>>
    fun fetchCities(id: Int): Flow<PagingData<Region>>

}