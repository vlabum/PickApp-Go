package ru.vlabum.pickappngo.viewmodels.home

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vlabum.pickappngo.data.models.CategoryItemData
import ru.vlabum.pickappngo.data.models.ProductItemData
import ru.vlabum.pickappngo.data.repositories.CategoriesDataFactory
import ru.vlabum.pickappngo.data.repositories.GoodsDataFactory
import ru.vlabum.pickappngo.data.repositories.HomeRepository
import ru.vlabum.pickappngo.data.repositories.ProductStrategy
import ru.vlabum.pickappngo.viewmodels.base.BaseViewModel
import ru.vlabum.pickappngo.viewmodels.base.IViewModelState
import java.util.concurrent.Executors

class HomeViewModel(handle: SavedStateHandle) :
    BaseViewModel<HomeState>(handle, HomeState()) {

    val repository = HomeRepository

    private val listConfig by lazy {
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .setPrefetchDistance(15)
            .setInitialLoadSizeHint(25)
            .build()
    }

    val listCustomerChoice = Transformations.switchMap(state) {
        buildPagedList(
            repository.customerChoice(),
            ::zeroLoadingHandleCustomerChoice,
            ::itemAtEndHandleCustomerChoice
        )
    }

    val listNewsOfWeek = Transformations.switchMap(state) {
        buildPagedList(
            repository.newsOfWeek(),
            ::zeroLoadingHandleNewsOfWeek,
            ::itemAtEndHandleNewsOfWeek
        )
    }

    val listGoodsOfWeek = Transformations.switchMap(state) {
        buildPagedList(
            repository.goodsOfWeek(),
            ::zeroLoadingHandleGoodsOfWeek,
            ::itemAtEndHandleGoodsOfWeek
        )
    }


    val listCategories = Transformations.switchMap(state) {
        buildPagedListCategory(
            repository.categories(),
            ::zeroLoadingHandleCategories,
            ::itemAtEndHandleCategories
        )
    }

    open fun observerCustomerChoiceList(
        owner: LifecycleOwner,
        onChange: (list: PagedList<ProductItemData>) -> Unit
    ) {
        listCustomerChoice.observe(owner, Observer { onChange(it) })
    }

    open fun observerNewsOfWeekList(
        owner: LifecycleOwner,
        onChange: (list: PagedList<ProductItemData>) -> Unit
    ) {
        listNewsOfWeek.observe(owner, Observer { onChange(it) })
    }

    open fun observerGoodsOfWeekList(
        owner: LifecycleOwner,
        onChange: (list: PagedList<ProductItemData>) -> Unit
    ) {
        listGoodsOfWeek.observe(owner, Observer { onChange(it) })
    }


    open fun observerCategoriesList(
        owner: LifecycleOwner,
        onChange: (list: PagedList<CategoryItemData>) -> Unit
    ) {
        listCategories.observe(owner, Observer { onChange(it) })
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

        //if all articles
        if (dataFactory.strategy is ProductStrategy.Offers) {
            builder.setBoundaryCallback(
                GoodsBoundaryCallback(
                    zeroLoadingHandle,
                    itemAtEndHandle
                )
            )
        }

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

    fun buildPagedListCategory(
        dataFactory: CategoriesDataFactory,
        zeroLoadingHandle: () -> Unit,
        itemAtEndHandle: (CategoryItemData) -> Unit
    ): LiveData<PagedList<CategoryItemData>> {

        val builder = LivePagedListBuilder<Int, CategoryItemData>(
            dataFactory,
            listConfig
        )

        //if all articles
        if (dataFactory.strategy is ProductStrategy.Categories) {
            builder.setBoundaryCallback(
                CategoriesBoundaryCallback(
                    zeroLoadingHandle,
                    itemAtEndHandle
                )
            )
        }

        return builder
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    //ВЫБОР ПОКУПАТЕЛЕЙ вызывается каждый раз, конда мы доскроливаем до конца нашего DataSource
    private fun itemAtEndHandleCustomerChoice(lastLoad: ProductItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            val items = repository.loadCustomerChoiceFromNetwork(
                start = lastLoad.id.toInt().inc(),
                size = listConfig.pageSize
            )
            if (items.isNotEmpty()) {
                repository.insertCustomerChoiceToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listCustomerChoice.value?.dataSource?.invalidate()
            }
        }
    }

    //ВЫБОР ПОКУПАТЕЛЕЙ
    private fun zeroLoadingHandleCustomerChoice() {
        viewModelScope.launch(Dispatchers.IO) {
            val items =
                repository.loadCustomerChoiceFromNetwork(
                    start = 0,
                    size = listConfig.initialLoadSizeHint
                )
            if (items.isNotEmpty()) {
                repository.insertCustomerChoiceToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listCustomerChoice.value?.dataSource?.invalidate()
            }
        }
    }


    //НОВИНКИ НЕДЕЛИ вызывается каждый раз, конда мы доскроливаем до конца нашего DataSource
    private fun itemAtEndHandleNewsOfWeek(lastLoad: ProductItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            val items = repository.loadNewsOfWeekFromNetwork(
                start = lastLoad.id.toInt().inc(),
                size = listConfig.pageSize
            )
            if (items.isNotEmpty()) {
                repository.insertNewsOfWeekToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listNewsOfWeek.value?.dataSource?.invalidate()
            }
        }
    }

    //НОВИНКИ НЕДЕЛИ
    private fun zeroLoadingHandleNewsOfWeek() {
        viewModelScope.launch(Dispatchers.IO) {
            val items =
                repository.loadNewsOfWeekFromNetwork(
                    start = 0,
                    size = listConfig.initialLoadSizeHint
                )
            if (items.isNotEmpty()) {
                repository.insertNewsOfWeekToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listNewsOfWeek.value?.dataSource?.invalidate()
            }
        }
    }


    //ТОВАРЫ НЕДЕЛИ вызывается каждый раз, конда мы доскроливаем до конца нашего DataSource
    private fun itemAtEndHandleGoodsOfWeek(lastLoad: ProductItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            val items = repository.loadGoodsOfWeekFromNetwork(
                start = lastLoad.id.toInt().inc(),
                size = listConfig.pageSize
            )
            if (items.isNotEmpty()) {
                repository.insertGoodsOfWeekToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listGoodsOfWeek.value?.dataSource?.invalidate()
            }
        }
    }

    //ТОВАРЫ НЕДЕЛИ
    private fun zeroLoadingHandleGoodsOfWeek() {
        viewModelScope.launch(Dispatchers.IO) {
            val items =
                repository.loadGoodsOfWeekFromNetwork(
                    start = 0,
                    size = listConfig.initialLoadSizeHint
                )
            if (items.isNotEmpty()) {
                repository.insertGoodsOfWeekToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listGoodsOfWeek.value?.dataSource?.invalidate()
            }
        }
    }


    //КАТЕГОРИИ
    private fun itemAtEndHandleCategories(lastLoad: CategoryItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            val items = repository.loadCategoriesFromNetwork(
                start = lastLoad.id.toInt().inc(),
                size = listConfig.pageSize
            )
            if (items.isNotEmpty()) {
                repository.insertCategoriesToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listCategories.value?.dataSource?.invalidate()
            }
        }
    }

    //КАТЕГОРИИ
    private fun zeroLoadingHandleCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val items =
                repository.loadCategoriesFromNetwork(
                    start = 0,
                    size = listConfig.initialLoadSizeHint
                )
            if (items.isNotEmpty()) {
                repository.insertCategoriesToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listCategories.value?.dataSource?.invalidate()
            }
        }
    }

    fun handleSearchMode(isSearch: Boolean) {
        updateState { it.copy(isSearch = isSearch) }
    }

    fun handleToggleLike(item: ProductItemData) {
        repository.toggleLike(item)
        updateState { it.copy() }
    }

}

data class HomeState(
    val isSearch: Boolean = false,
    val searchQuery: String? = null,
    val isLoading: Boolean = true
) : IViewModelState

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


class CategoriesBoundaryCallback(
    private val zeroLoadingHandle: () -> Unit,
    private val itemAtEndHandle: (CategoryItemData) -> Unit
) : PagedList.BoundaryCallback<CategoryItemData>() {
    override fun onZeroItemsLoaded() {
        //Storage is empty
        zeroLoadingHandle()
    }

    override fun onItemAtEndLoaded(itemAtEnd: CategoryItemData) {
        //user scroll down -> need load more items
        itemAtEndHandle(itemAtEnd)
    }
}