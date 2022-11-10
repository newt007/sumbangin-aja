package com.bintangpoetra.sumbanginaja.data.region.factory

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bintangpoetra.sumbanginaja.data.region.remote.RegionService
import com.bintangpoetra.sumbanginaja.domain.region.mapper.toListDomain
import com.bintangpoetra.sumbanginaja.domain.region.model.Region
import com.bintangpoetra.sumbanginaja.utils.ConstVal.DEFAULT_PAGE_INDEX
import com.bintangpoetra.sumbanginaja.utils.ConstVal.DEFAULT_PAGE_SIZE

class CityPagingFactory(
    private val service: RegionService,
    private val idProvince: Int,
) : PagingSource<Int, Region>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Region> {
        val result = service.fetchCities(idProvince)
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return LoadResult.Page(
            data = result.data.toListDomain(),
            nextKey = if ((result.data.size / DEFAULT_PAGE_SIZE) < page) null else page + 1,
            prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Region>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}