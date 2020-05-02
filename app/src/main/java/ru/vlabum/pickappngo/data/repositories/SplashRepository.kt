package ru.vlabum.pickappngo.data.repositories

import androidx.lifecycle.LiveData
import ru.vlabum.pickappngo.data.LocalDataHolder
import ru.vlabum.pickappngo.data.SplashItemData

object SplashRepository {
    fun getSplashData(): LiveData<SplashItemData> = LocalDataHolder.getSplashData()
}