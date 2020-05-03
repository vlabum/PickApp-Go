package ru.vlabum.pickappngo.data

import androidx.lifecycle.MutableLiveData

object LocalDataHolder {

    fun getSplashData(): MutableLiveData<SplashItemData> {
        return MutableLiveData(SplashItemData())
    }

    fun loadCategory(): MutableLiveData<List<CategoryItemData>> {
        return MutableLiveData(
            listOf(
                CategoryItemData("1", "Category1"),
                CategoryItemData("2", "Category2"),
                CategoryItemData("3", "Category3"),
                CategoryItemData("4", "Category4"),
                CategoryItemData("5", "Category5"),
                CategoryItemData("6", "Category6")
            )
        )
    }

}

data class SplashItemData(
    val id: String = "0"
)

data class CategoryItemData(
    val id: String = "0",
    val caption: String = "new catalog",
    val picture: String? = null
)