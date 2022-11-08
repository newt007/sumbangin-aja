package com.bintangpoetra.sumbanginaja.data.region

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bintangpoetra.sumbanginaja.data.region.factory.CityPagingFactory
import com.bintangpoetra.sumbanginaja.data.region.factory.RegionPagingFactory
import com.bintangpoetra.sumbanginaja.data.region.remote.RegionService
import com.bintangpoetra.sumbanginaja.domain.region.model.Region
import com.bintangpoetra.sumbanginaja.utils.ConstVal.DEFAULT_PAGE_SIZE
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import kotlinx.coroutines.flow.Flow

class RegionDataStore(
    private val api: RegionService,
    private val pref: PreferenceManager
): RegionRepository {

    override fun fetchProvinces(): Flow<PagingData<Region>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                RegionPagingFactory(api, pref)
            }
        ).flow
    }

    override fun fetchCities(id: Int): Flow<PagingData<Region>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                CityPagingFactory(api, id, pref)
            }
        ).flow
    }

}