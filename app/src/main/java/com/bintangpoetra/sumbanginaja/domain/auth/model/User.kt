package com.bintangpoetra.sumbanginaja.domain.auth.model

import com.bintangpoetra.sumbanginaja.utils.ext.emptyString

data class User(
    val id: Int = 0,
    val name: String = emptyString(),
    val email: String = emptyString(),
    val emailVerifiedAt: String = emptyString(),
    val profileUsers: String = emptyString(),
    val phoneNumber: String = emptyString(),
    val address: String = emptyString(),
    val token: String = emptyString()
)
