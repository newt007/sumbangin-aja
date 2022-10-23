package com.bintangpoetra.sumbanginaja.domain.region.model

import com.bintangpoetra.sumbanginaja.data.auth.model.UserItem
import com.bintangpoetra.sumbanginaja.utils.ext.emptyString

data class Region(
    val id: Int = 0,
    val name: String = emptyString(),
)
