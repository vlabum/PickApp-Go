package ru.vlabum.pickappngo.data.repositories

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import ru.vlabum.pickappngo.data.LocalDataHolder
import ru.vlabum.pickappngo.data.NetworkDataHolder
import ru.vlabum.pickappngo.data.models.ProductItemData

object HomeRepository {

    private val local = LocalDataHolder
    private val network = NetworkDataHolder

    fun customerChoice(): HomeDataFactory =
        HomeDataFactory(ProductStrategy.CustomerChoice(::customerChoiceProvider))

    //наш itemProvider
    private fun customerChoiceProvider(start: Int, size: Int) =
        local.localArticleItems
            .drop(start)
            .take(size)


    //загрузка из сети
    fun loadArticlesFromNetwork(start: Int, size: Int): List<ProductItemData> =
        network.networkCustomerChoice
            .drop(start)
            .take(size)
            .apply { Thread.sleep(500) }


    //кеширование
    fun insertArticlesToDb(articles: List<ProductItemData>) {
        local.localArticleItems.addAll(articles)
            .apply { Thread.sleep(100) }
    }

}

class HomeDataFactory(val strategy: ProductStrategy) :
    DataSource.Factory<Int, ProductItemData>() {
    override fun create(): DataSource<Int, ProductItemData> = ProductDataSource(strategy)
}

class ProductDataSource(private val strategy: ProductStrategy) :
    PositionalDataSource<ProductItemData>() {
    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<ProductItemData>
    ) {
        // начальные значения про загрузке нашего листа
        val result = strategy.getItems(params.requestedStartPosition, params.requestedLoadSize)
        callback.onResult(result, params.requestedStartPosition)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<ProductItemData>) {
        val result = strategy.getItems(params.startPosition, params.loadSize)
        callback.onResult(result)
    }
}


sealed class ProductStrategy() {

    abstract fun getItems(start: Int, size: Int): List<ProductItemData>

    class CustomerChoice(
        private val itemProvider: (Int, Int) -> List<ProductItemData>
    ) : ProductStrategy() {
        override fun getItems(start: Int, size: Int): List<ProductItemData> =
            itemProvider(start, size)
    }

}
