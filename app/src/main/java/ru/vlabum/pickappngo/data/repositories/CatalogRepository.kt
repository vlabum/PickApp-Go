package ru.vlabum.pickappngo.data.repositories

import androidx.lifecycle.LiveData
import ru.vlabum.pickappngo.data.CategoryItemData
import ru.vlabum.pickappngo.data.LocalDataHolder

object CatalogRepository {
    fun loadCategory(): LiveData<List<CategoryItemData>> = LocalDataHolder.loadCategory()
}