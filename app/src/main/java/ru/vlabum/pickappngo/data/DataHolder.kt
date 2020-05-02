package ru.vlabum.pickappngo.data

import androidx.lifecycle.MutableLiveData
import java.util.*

object LocalDataHolder {

    fun getSplashData(): MutableLiveData<SplashItemData> {
        return MutableLiveData(SplashItemData())
    }

}

data class SplashItemData(
    val id: String = "0"
)