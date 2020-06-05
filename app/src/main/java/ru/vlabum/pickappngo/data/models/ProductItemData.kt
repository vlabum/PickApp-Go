package ru.vlabum.pickappngo.data.models

import java.math.BigDecimal
import java.util.*

data class ProductItemData(
    val id: String,
    val idCategory: String,
    val title: String ,
    val price: Int, // в копейках
    val description: String = "",
    val imageUrl: String = "",
    val imageId: Int = 0,
    val dimension: String = "",
    var isLike: Boolean = false
)
