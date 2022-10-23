package com.bintangpoetra.sumbanginaja.domain.region.mapper

import com.bintangpoetra.sumbanginaja.data.region.model.RegionItem
import com.bintangpoetra.sumbanginaja.domain.region.model.Region
import com.bintangpoetra.sumbanginaja.utils.ext.emptyString
import com.bintangpoetra.sumbanginaja.utils.ext.orZero

fun RegionItem.toDomain(): Region {
    return Region(
        id = this.id.orZero(),
        name = this.name ?: emptyString(),
    )
}

fun List<RegionItem>.toListDomain(): List<Region> {
    val arrayList = ArrayList<Region>()
    this.forEach {
        arrayList.add(it.toDomain())
    }
    return arrayList
}