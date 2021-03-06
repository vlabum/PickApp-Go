package ru.vlabum.pickappngo.viewmodels.catalog

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vlabum.pickappngo.data.CategoryItemData
import ru.vlabum.pickappngo.data.models.ProductItemData
import ru.vlabum.pickappngo.data.repositories.CatalogRepository
import ru.vlabum.pickappngo.data.repositories.GoodsDataFactory
import ru.vlabum.pickappngo.data.repositories.HomeRepository
import ru.vlabum.pickappngo.data.repositories.ProductStrategy
import ru.vlabum.pickappngo.viewmodels.base.BaseViewModel
import ru.vlabum.pickappngo.viewmodels.base.IViewModelState
import ru.vlabum.pickappngo.viewmodels.common.GoodsBoundaryCallback
import ru.vlabum.pickappngo.viewmodels.splash.ISplashViewModel
import java.util.concurrent.Executors

class CatalogViewModel(handle: SavedStateHandle) :
    BaseViewModel<CatalogState>(handle, CatalogState()) {

    val repository = HomeRepository

    private val listConfig by lazy {
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .setPrefetchDistance(15)
            .setInitialLoadSizeHint(25)
            .build()
    }

    val listAllGoods = Transformations.switchMap(state) {
        buildPagedList(
            repository.allGoods(),
            ::zeroLoadingHandleAllGoods,
            ::itemAtEndHandleAllGoods
        )
    }

    open fun observerAllGoods(
        owner: LifecycleOwner,
        onChange: (list: PagedList<ProductItemData>) -> Unit
    ) {
        listAllGoods.observe(owner, Observer { onChange(it) })
    }


    fun buildPagedList(
        dataFactory: GoodsDataFactory,
        zeroLoadingHandle: () -> Unit,
        itemAtEndHandle: (ProductItemData) -> Unit
    ): LiveData<PagedList<ProductItemData>> {

        val builder = LivePagedListBuilder<Int, ProductItemData>(
            dataFactory,
            listConfig
        )

        if (dataFactory.strategy is ProductStrategy.AllGoods) {
            builder.setBoundaryCallback(
                GoodsBoundaryCallback(
                    zeroLoadingHandle,
                    itemAtEndHandle
                )
            )
        }

        return builder
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .build()
    }


    //ВСЕ ТОВАРЫ вызывается каждый раз, конда мы доскроливаем до конца нашего DataSource
    private fun itemAtEndHandleAllGoods(lastLoad: ProductItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            val items = repository.loadAllGoodsFromNetwork(
                start = lastLoad.id.toInt().inc(),
                size = listConfig.pageSize
            )
            if (items.isNotEmpty()) {
                repository.insertAllGoodsToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listAllGoods.value?.dataSource?.invalidate()
            }
        }
    }

    //ВСЕ ТОВАРЫ
    private fun zeroLoadingHandleAllGoods() {
        viewModelScope.launch(Dispatchers.IO) {
            val items =
                repository.loadAllGoodsFromNetwork(
                    start = 0,
                    size = listConfig.initialLoadSizeHint
                )
            if (items.isNotEmpty()) {
                repository.insertAllGoodsToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listAllGoods.value?.dataSource?.invalidate()
            }
        }
    }

    fun handleSearchMode(isSearch: Boolean) {
        updateState { it.copy(isSearch = isSearch) }
    }

    fun handleToggleLike(item: ProductItemData) {
        repository.insertGoodsToDb(item)
        repository.toggleLike(item)
        updateState { it.copy(isSearch = false) }
        listAllGoods.value?.dataSource?.invalidate()
    }

}

data class CatalogState(
    val isSearch: Boolean = false,
    val searchQuery: String? = null
) : IViewModelState {


    override fun save(outState: SavedStateHandle) {
        super.save(outState)
    }

    override fun restore(savedState: SavedStateHandle): IViewModelState {
        return super.restore(savedState)
    }
}



