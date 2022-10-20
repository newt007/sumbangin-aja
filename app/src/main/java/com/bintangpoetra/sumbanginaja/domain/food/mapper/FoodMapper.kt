package com.bintangpoetra.sumbanginaja.domain.food.mapper

import com.bintangpoetra.sumbanginaja.data.food.model.FoodItem
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import com.bintangpoetra.sumbanginaja.utils.ext.emptyString
import com.bintangpoetra.sumbanginaja.utils.ext.orZero

fun FoodItem.toDomain(): Food {
    return Food(
        id = this.id.orZero(),
        name = this.name ?: emptyString(),
        images = this.images ?: emptyString(),
        descriptions = this.descriptions ?: emptyString(),
        paybackTime = this.paybackTime ?: emptyString(),
        userID = this.userID.orZero(),
        latitude = this.latitude.orZero(),
        longitude = this.longitude.orZero(),
        createdAt = this.createdAt ?: emptyString(),
        updatedAt = this.updatedAt ?: emptyString(),
        user = this.user
    )
}

fun List<FoodItem>.toListDomain(): List<Food> {
    val arrayList = ArrayList<Food>()
    this.forEach {
        arrayList.add(it.toDomain())
    }
    return arrayList
}