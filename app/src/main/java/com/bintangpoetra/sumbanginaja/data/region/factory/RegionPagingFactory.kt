package com.bintangpoetra.sumbanginaja.data.region.factory

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bintangpoetra.sumbanginaja.data.region.remote.RegionService
import com.bintangpoetra.sumbanginaja.domain.region.mapper.toListDomain
import com.bintangpoetra.sumbanginaja.domain.region.model.Region
import com.bintangpoetra.sumbanginaja.utils.ConstVal.DEFAULT_PAGE_INDEX
import com.bintangpoetra.sumbanginaja.utils.ConstVal.DEFAULT_PAGE_SIZE
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import com.bintangpoetra.sumbanginaja.utils.ext.toBearer

class RegionPagingFactory(
    private val service: RegionService,
    private val pref: PreferenceManager
) : PagingSource<Int, Region>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Region> {
        val result = service.fetchProvinces(pref.getToken.toBearer())
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