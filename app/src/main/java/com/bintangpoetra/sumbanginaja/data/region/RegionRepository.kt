package com.bintangpoetra.sumbanginaja.data.region

import androidx.paging.PagingData
import com.bintangpoetra.sumbanginaja.domain.region.model.Region
import kotlinx.coroutines.flow.Flow

interface RegionRepository {

    fun fetchProvinces(): Flow<PagingData<Region>>
    fun fetchCities(id: Int): Flow<PagingData<Region>>

}