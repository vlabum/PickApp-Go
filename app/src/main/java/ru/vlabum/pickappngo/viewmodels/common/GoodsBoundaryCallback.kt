package ru.vlabum.pickappngo.viewmodels.common

import androidx.paging.PagedList
import ru.vlabum.pickappngo.data.models.ProductItemData

//для уведомлений о том, что в нашем dataSource закончились данные, типо доскролили до самого низа
class GoodsBoundaryCallback(
    private val zeroLoadingHandle: () -> Unit,
    private val itemAtEndHandle: (ProductItemData) -> Unit
) : PagedList.BoundaryCallback<ProductItemData>() {
    override fun onZeroItemsLoaded() {
        //Storage is empty
        zeroLoadingHandle()
    }

    override fun onItemAtEndLoaded(itemAtEnd: ProductItemData) {
        //user scroll down -> need load more items
        itemAtEndHandle(itemAtEnd)
    }
}