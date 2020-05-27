package ru.vlabum.pickappngo.viewmodels.home

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vlabum.pickappngo.data.models.ProductItemData
import ru.vlabum.pickappngo.data.repositories.HomeDataFactory
import ru.vlabum.pickappngo.data.repositories.HomeRepository
import ru.vlabum.pickappngo.data.repositories.ProductStrategy
import ru.vlabum.pickappngo.viewmodels.base.BaseViewModel
import ru.vlabum.pickappngo.viewmodels.base.IViewModelState
import ru.vlabum.pickappngo.viewmodels.base.Notify
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

    val listData = Transformations.switchMap(state) {
            buildPagedList(repository.customerChoice())
    }

    open fun observerList(
        owner: LifecycleOwner,
        onChange: (list: PagedList<ProductItemData>) -> Unit
    ) {
        listData.observe(owner, Observer { onChange(it) })
    }

    fun buildPagedList(
        dataFactory: HomeDataFactory
    ): LiveData<PagedList<ProductItemData>> {

        val builder = LivePagedListBuilder<Int, ProductItemData>(
            dataFactory,
            listConfig
        )

        //if all articles
        if (dataFactory.strategy is ProductStrategy.CustomerChoice) {
            builder.setBoundaryCallback(
                ArticlesBoundaryCallback(
                    ::zeroLoadingHandle,
                    ::itemAtEndHandle
                )
            )
        }

        return builder
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    //вызывается каждый раз, конда мы доскроливаем до конца нашего DataSource
    private fun itemAtEndHandle(lastLoadArticle: ProductItemData) {
        Log.e("HomeViewModel", "itemAtEndHandle: ")
        viewModelScope.launch(Dispatchers.IO) {
            val items = repository.loadArticlesFromNetwork(
                start = lastLoadArticle.id.toInt().inc(),
                size = listConfig.pageSize
            )
            if (items.isNotEmpty()) {
                repository.insertArticlesToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listData.value?.dataSource?.invalidate()
            }

            withContext(Dispatchers.Main) {
                notify(
                    Notify.TextMessage(
                        "Load from network articles from ${items.firstOrNull()?.id} " +
                                "to ${items.lastOrNull()?.id}"
                    )
                )
            }
        }
    }

    private fun zeroLoadingHandle() {
        Log.e("HomeViewModel", "zeroLoadingHandle: ")
        notify(Notify.TextMessage("Storage is empty"))
        viewModelScope.launch(Dispatchers.IO) {
            val items =
                repository.loadArticlesFromNetwork(
                    start = 0,
                    size = listConfig.initialLoadSizeHint
                )
            if (items.isNotEmpty()) {
                repository.insertArticlesToDb(items)
                //invalidate data in data source -> create new LiveData<PagedList>
                listData.value?.dataSource?.invalidate()
            }
        }
    }


}

data class HomeState(
    val isSearch: Boolean = false,
    val searchQuery: String? = null,
    val isLoading: Boolean = true
) : IViewModelState

//для уведомлений о том, что в нашем dataSource закончились данные, типо доскролили до самого низа
class ArticlesBoundaryCallback(
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
