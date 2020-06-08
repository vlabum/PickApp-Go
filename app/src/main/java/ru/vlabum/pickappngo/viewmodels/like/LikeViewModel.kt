package ru.vlabum.pickappngo.viewmodels.like

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vlabum.pickappngo.data.models.ProductItemData
import ru.vlabum.pickappngo.data.repositories.GoodsDataFactory
import ru.vlabum.pickappngo.data.repositories.HomeRepository
import ru.vlabum.pickappngo.data.repositories.ProductStrategy
import ru.vlabum.pickappngo.viewmodels.base.BaseViewModel
import ru.vlabum.pickappngo.viewmodels.base.IViewModelState
import ru.vlabum.pickappngo.viewmodels.common.GoodsBoundaryCallback
import java.util.concurrent.Executors

class LikeViewModel(handle: SavedStateHandle) :
    BaseViewModel<LikeState>(handle, LikeState()) {

    val repository = HomeRepository

    private val listConfig by lazy {
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .setPrefetchDistance(15)
            .setInitialLoadSizeHint(25)
            .build()
    }

    val listLikeGoods = Transformations.switchMap(state) {
        buildPagedList(
            repository.likeGoods(),
            ::zeroLoadingHandleAllGoods,
            ::itemAtEndHandleAllGoods
        )
    }

    open fun observerLikeGoods(
        owner: LifecycleOwner,
        onChange: (list: PagedList<ProductItemData>) -> Unit
    ) {
        listLikeGoods.observe(owner, Observer { onChange(it) })
    }

    //TODO вынести в базовый класс
    fun buildPagedList(
        dataFactory: GoodsDataFactory,
        zeroLoadingHandle: () -> Unit,
        itemAtEndHandle: (ProductItemData) -> Unit
    ): LiveData<PagedList<ProductItemData>> {

        val builder = LivePagedListBuilder<Int, ProductItemData>(
            dataFactory,
            listConfig
        )

        if (dataFactory.strategy is ProductStrategy.LikeGoods) {
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


    //ЛЮБИМЫЕ ТОВАРЫ вызывается каждый раз, конда мы доскроливаем до конца нашего DataSource
    private fun itemAtEndHandleAllGoods(lastLoad: ProductItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            val items = repository.loadAllGoodsFromNetwork(
                start = lastLoad.id.toInt().inc(),
                size = listConfig.pageSize
            )
            if (items.isNotEmpty()) {
                repository.insertAllGoodsToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listLikeGoods.value?.dataSource?.invalidate()
            }
        }
    }

    //ЛЮБИМЫЕ ТОВАРЫ
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
                listLikeGoods.value?.dataSource?.invalidate()
            }
        }
    }

    fun handleSearchMode(isSearch: Boolean) {
        updateState { it.copy(isSearch = isSearch) }
    }

    fun handleToggleLike(item: ProductItemData) {
        repository.toggleLike(item)
        updateState { it.copy() }
        listLikeGoods.value?.dataSource?.invalidate()
    }

}

data class LikeState(
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


