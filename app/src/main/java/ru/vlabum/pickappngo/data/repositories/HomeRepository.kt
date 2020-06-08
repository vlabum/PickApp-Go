package ru.vlabum.pickappngo.data.repositories

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import ru.vlabum.pickappngo.data.LocalDataHolder
import ru.vlabum.pickappngo.data.NetworkDataHolder
import ru.vlabum.pickappngo.data.models.CategoryItemData
import ru.vlabum.pickappngo.data.models.ProductItemData

object HomeRepository {

    private val local = LocalDataHolder
    private val network = NetworkDataHolder

    fun customerChoice(): GoodsDataFactory =
        GoodsDataFactory(ProductStrategy.Offers(::customerChoiceProvider))

    fun newsOfWeek(): GoodsDataFactory =
        GoodsDataFactory(ProductStrategy.Offers(::newsOfWeekProvider))

    fun goodsOfWeek(): GoodsDataFactory =
        GoodsDataFactory(ProductStrategy.Offers(::goodsOfWeekProvider))

    fun allGoods(): GoodsDataFactory =
        GoodsDataFactory(ProductStrategy.AllGoods(::allGoodsProvider))

    fun likeGoods(): GoodsDataFactory =
        GoodsDataFactory(ProductStrategy.LikeGoods(::likeGoodsProvider))

    fun categories(): CategoriesDataFactory =
        CategoriesDataFactory(ProductStrategy.Categories(::categoryProvider))

    //наш itemProvider
    private fun customerChoiceProvider(start: Int, size: Int) =
        local.locCustomerChoice.drop(start).take(size)

    private fun newsOfWeekProvider(start: Int, size: Int) =
        local.locNewsOfweek.drop(start).take(size)

    private fun goodsOfWeekProvider(start: Int, size: Int) =
        local.locGoodsOfweek.drop(start).take(size)

    private fun allGoodsProvider(start: Int, size: Int) =
        local.locAllGoods.drop(start).take(size)

    private fun likeGoodsProvider(start: Int, size: Int) =
        local.locAllGoods
            .asSequence()
            .filter { it.isLike }
            .drop(start)
            .take(size)
            .toList()

    private fun categoryProvider(start: Int, size: Int) =
        local.locCategories.drop(start).take(size)


    //"ВЫБОР ПОКУПАТЕЛЕЙ" загрузка из сети
    fun loadCustomerChoiceFromNetwork(start: Int, size: Int): List<ProductItemData> =
        network.netCustomerChoice
            .drop(start)
            .take(size)
            .apply { Thread.sleep(500) }


    //"ВЫБОР ПОКУПАТЕЛЕЙ" кеширование
    fun insertCustomerChoiceToDb(goods: List<ProductItemData>) {
        local.locCustomerChoice.addAll(goods)
            .apply { Thread.sleep(100) }
    }


    //"НОВИНКИ НЕДЕЛИ" загрузка из сети
    fun loadNewsOfWeekFromNetwork(start: Int, size: Int): List<ProductItemData> =
        network.netNewsOfweek
            .drop(start)
            .take(size)
            .apply { Thread.sleep(500) }

    //"НОВИНКИ НЕДЕЛИ" кеширование
    fun insertNewsOfWeekToDb(goods: List<ProductItemData>) {
        local.locNewsOfweek.addAll(goods)
            .apply { Thread.sleep(100) }
    }


    //"ТОВАРЫ НЕДЕЛИ" загрузка из сети
    fun loadGoodsOfWeekFromNetwork(start: Int, size: Int): List<ProductItemData> =
        network.netGoodsOfweek
            .drop(start)
            .take(size)
            .apply { Thread.sleep(500) }

    //"ТОВАРЫ НЕДЕЛИ" кеширование
    fun insertGoodsOfWeekToDb(goods: List<ProductItemData>) {
        local.locGoodsOfweek.addAll(goods)
            .apply { Thread.sleep(100) }
    }

    //"КАТЕГОРИИ
    fun loadCategoriesFromNetwork(start: Int, size: Int): List<CategoryItemData> =
        network.netCategories
            .drop(start)
            .take(size)
            .apply { Thread.sleep(500) }

    //КАТЕГОРИИ
    fun insertCategoriesToDb(categories: List<CategoryItemData>) {
        local.locCategories.addAll(categories)
            .apply { Thread.sleep(100) }
    }

    //ВСЕ ПРОДУКТЫ загрузка из сети
    fun loadAllGoodsFromNetwork(start: Int, size: Int): List<ProductItemData> =
        network.netAllGoods
            .drop(start)
            .take(size)
            .apply { Thread.sleep(500) }


    //ВСЕ ПРОДУКТЫ кеширование
    fun insertAllGoodsToDb(goods: List<ProductItemData>) {
        goods.forEach {
            insertGoodsToDb(it)
        }
            .apply { Thread.sleep(100) }
        //local.locAllGoods.addAll(goods).apply { Thread.sleep(100) }
    }

    fun insertGoodsToDb(goods: ProductItemData) {
        if (!local.locAllGoods.contains(goods)) {
            local.locAllGoods.add(goods)
        }
    }

    fun toggleLike(item: ProductItemData) {
        LocalDataHolder.locAllGoods.find { it.id == item.id }?.isLike = !item.isLike
    }

}


class GoodsDataFactory(val strategy: ProductStrategy) :
    DataSource.Factory<Int, ProductItemData>() {
    override fun create(): DataSource<Int, ProductItemData> = GoodsDataSource(strategy)
}

class CategoriesDataFactory(val strategy: ProductStrategy) :
    DataSource.Factory<Int, CategoryItemData>() {
    override fun create(): DataSource<Int, CategoryItemData> = CategoriesDataSource(strategy)
}


class GoodsDataSource(private val strategy: ProductStrategy) :
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


class CategoriesDataSource(private val strategy: ProductStrategy) :
    PositionalDataSource<CategoryItemData>() {
    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<CategoryItemData>
    ) {
        // начальные значения про загрузке нашего листа
        val result = strategy.getCategory(params.requestedStartPosition, params.requestedLoadSize)
        callback.onResult(result, params.requestedStartPosition)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<CategoryItemData>) {
        val result = strategy.getCategory(params.startPosition, params.loadSize)
        callback.onResult(result)
    }
}


sealed class ProductStrategy() {

    open fun getItems(start: Int, size: Int): List<ProductItemData> = emptyList()
    open fun getCategory(start: Int, size: Int): List<CategoryItemData> = emptyList()

    class Offers(
        private val itemProvider: (Int, Int) -> List<ProductItemData>
    ) : ProductStrategy() {
        override fun getItems(start: Int, size: Int): List<ProductItemData> =
            itemProvider(start, size)
    }

    class AllGoods(
        private val itemProvider: (Int, Int) -> List<ProductItemData>
    ) : ProductStrategy() {
        override fun getItems(start: Int, size: Int): List<ProductItemData> =
            itemProvider(start, size)
    }

    class LikeGoods(
        private val itemProvider: (Int, Int) -> List<ProductItemData>
    ) : ProductStrategy() {
        override fun getItems(start: Int, size: Int): List<ProductItemData> =
            itemProvider(start, size)
    }

    class Categories(
        private val itemProvider: (Int, Int) -> List<CategoryItemData>
    ) : ProductStrategy() {
        override fun getCategory(start: Int, size: Int): List<CategoryItemData> =
            itemProvider(start, size)
    }

}
