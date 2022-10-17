package com.bintangpoetra.sumbanginaja.domain.auth.mapper

import com.bintangpoetra.sumbanginaja.data.auth.model.UserItem
import com.bintangpoetra.sumbanginaja.domain.auth.model.User
import com.bintangpoetra.sumbanginaja.utils.ext.emptyString
import com.bintangpoetra.sumbanginaja.utils.ext.orZero

fun UserItem.toDomain(): User {
    return User(
        id = this.id.orZero(),
        name = this.name ?: emptyString(),
        email = this.email ?: emptyString(),
        emailVerifiedAt = this.emailVerifiedAt ?: emptyString(),
        profileUsers = this.profileUsers ?: emptyString(),
        phoneNumber = this.phoneNumber ?: emptyString(),
        address = this.address ?: emptyString(),
        token = this.token ?: emptyString()
    )
}