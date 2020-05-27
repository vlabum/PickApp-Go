package ru.vlabum.pickappngo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.vlabum.pickappngo.data.EntityGenerator.generateCustomerChoiceItems
import ru.vlabum.pickappngo.data.models.ProductItemData
import java.util.*

object LocalDataHolder {

    val localArticleItems: MutableList<ProductItemData> = mutableListOf()

    fun getSplashData(): MutableLiveData<SplashItemData> {
        return MutableLiveData(SplashItemData())
    }

    fun loadCategory(): MutableLiveData<List<CategoryItemData>> {
        return MutableLiveData(
            listOf(
                CategoryItemData("1", "Category1", "category_milk.png"),
                CategoryItemData("2", "Category2", "category_milk.png"),
                CategoryItemData("3", "Category3", "category_milk.png"),
                CategoryItemData("4", "Category4", "category_milk.png"),
                CategoryItemData("5", "Category5", "category_milk.png"),
                CategoryItemData("6", "Category6", "category_milk.png"),
                CategoryItemData("7", "Category7", "category_milk.png"),
                CategoryItemData("8", "Category8", "category_milk.png"),
                CategoryItemData("9", "Category9", "category_milk.png"),
                CategoryItemData("10", "Category10", "category_milk.png")
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

object NetworkDataHolder {

    val networkCustomerChoice: List<ProductItemData> = generateCustomerChoiceItems(30)

}