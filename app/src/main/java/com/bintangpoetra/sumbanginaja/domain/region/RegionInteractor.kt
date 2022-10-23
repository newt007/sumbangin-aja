package com.bintangpoetra.sumbanginaja.domain.region

import androidx.paging.PagingData
import com.bintangpoetra.sumbanginaja.data.food.FoodRepository
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.data.region.RegionRepository
import com.bintangpoetra.sumbanginaja.domain.region.model.Region
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class RegionInteractor(
    private val repository: RegionRepository
) : RegionUseCase {

    override fun fetchProvinces(): Flow<PagingData<Region>> {
        return repository.fetchProvinces().flowOn(Dispatchers.IO)
    }

    override fun fetchCities(id: Int): Flow<PagingData<Region>> {
        return repository.fetchCities(id).flowOn(Dispatchers.IO)
    }

}