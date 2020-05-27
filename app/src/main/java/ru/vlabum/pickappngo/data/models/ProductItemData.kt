package ru.vlabum.pickappngo.data.models

import java.util.*

data class ProductItemData(
    val id: String,
    val title: String,
    val price: Float,
    val description: String,
    val imageUrl: String,
    val imageId: Int
)
