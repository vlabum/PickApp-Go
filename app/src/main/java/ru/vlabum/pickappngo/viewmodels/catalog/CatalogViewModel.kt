package ru.vlabum.pickappngo.viewmodels.catalog

import androidx.lifecycle.SavedStateHandle
import ru.vlabum.pickappngo.data.CategoryItemData
import ru.vlabum.pickappngo.data.repositories.CatalogRepository
import ru.vlabum.pickappngo.viewmodels.base.BaseViewModel
import ru.vlabum.pickappngo.viewmodels.base.IViewModelState
import ru.vlabum.pickappngo.viewmodels.splash.ISplashViewModel

class CatalogViewModel(handle: SavedStateHandle) :
    BaseViewModel<CategoryState>(handle, CategoryState()), ISplashViewModel {

    val repository = CatalogRepository

    init {
        subscribeOnDataSource(repository.loadCategory()) { data, state ->
            data ?: return@subscribeOnDataSource null
            state.copy(category = data)
        }
    }

}

data class CategoryState(
    val context: String = "0",
    val category: List<CategoryItemData> = emptyList()
) : IViewModelState {
    override fun save(outState: SavedStateHandle) {
        super.save(outState)
    }

    override fun restore(savedState: SavedStateHandle): IViewModelState {
        return super.restore(savedState)
    }
}