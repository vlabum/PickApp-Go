package ru.vlabum.pickappngo.viewmodels.splash

import androidx.lifecycle.SavedStateHandle
import ru.vlabum.pickappngo.data.SplashItemData
import ru.vlabum.pickappngo.data.repositories.SplashRepository
import ru.vlabum.pickappngo.viewmodels.base.BaseViewModel
import ru.vlabum.pickappngo.viewmodels.base.IViewModelState

class SplashViewModel(handle: SavedStateHandle) :
    BaseViewModel<SplashState>(handle, SplashState()), ISplashViewModel {

    val repository = SplashRepository

    init {
        subscribeOnDataSource(repository.getSplashData()) { data, state ->
            data ?: return@subscribeOnDataSource null
            state.copy(data = data)
        }
    }

}

data class SplashState(
    val data: SplashItemData = SplashItemData()
) : IViewModelState {
    override fun save(outState: SavedStateHandle) {
        super.save(outState)
    }

    override fun restore(savedState: SavedStateHandle): IViewModelState {
        return super.restore(savedState)
    }
}