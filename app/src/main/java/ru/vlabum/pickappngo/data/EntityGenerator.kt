package ru.vlabum.pickappngo.data

import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.data.models.*
import ru.vlabum.pickappngo.data.models.CategoryItemData

object EntityGenerator {


    fun generateCustomerChoiceItems(count: Int): List<ProductItemData> =
        Array(count) { productItems[it % 6] }
            .toList()
            .mapIndexed { index, article ->
                article.copy(
                    id = "$index"
                )
            }

    fun getCategories(): List<CategoryItemData> = categoryItemData
}

private val categoryItemData = Array(size = 5) {
    when(it) {
        1 -> CategoryItemData(
            id = 1,
            idStr = "vegetable",
            title = "Овощи, фрукты, ягоды, грибы",
            imageUrl = R.drawable.cat_vegetable2
        )
        2 -> CategoryItemData(
            id = 2,
            idStr = "milk",
            title = "Молоко, сыр и яйцо",
            imageUrl = R.drawable.cat_milk
        )
        3 -> CategoryItemData(
            id = 3,
            idStr = "meat",
            title = "Мясо, птица, колбасы",
            imageUrl = R.drawable.cat_meat
        )
        4 -> CategoryItemData(
            id = 4,
            idStr = "grocery",
            title = "Макароны, крупы, специи",
            imageUrl = R.drawable.cat_grocery
        )
        else -> CategoryItemData(
            id = 5,
            idStr = "fish",
            title = "Рыба и морепродукты",
            imageUrl = R.drawable.cat_fish
        )
    }
}.toList()

private val productItems = Array(6) {
    when (it) {
        1 -> ProductItemData(
            id = "0",
            imageUrl = "https://skill-branch.ru/img/mail/bot/android-category.png",
            imageId = R.drawable.pitm_hotpng_5,
            title = "Кошачий корм\nPurina «Proplan»",
            description = "LiveData and the Fragment lifecycle",
            dimension = "200 г",
            price = 1111
        )

        2 -> ProductItemData(
            id = "0",
            imageId = R.drawable.pitm_hotpng_4,
            imageUrl = "https://skill-branch.ru/img/mail/bot/android-category.png",
            title = "Молоко\n" +
                    "«Real goodness»",
            description = "Article describing usage of Safe args Gradle plugin with the Navigation Architecture Component and current support for argument types",
            dimension = "200 г",
            price = 2222
        )

        3 -> ProductItemData(
            id = "0",
            imageUrl = "https://skill-branch.ru/img/mail/bot/android-category.png",
            imageId = R.drawable.pitm_image_4,
            title = "Observe LiveData",
            description = "Google introduced Android architecture components which are basically a collection of libraries that facilitate robust design, testable",
            dimension = "шт",
            price = 3333
        )

        4 -> ProductItemData(
            id = "0",
            imageUrl = "https://skill-branch.ru/img/mail/bot/android-category.png",
            imageId = R.drawable.pitm_image_6,
            title = "The New Android",
            description = "How to integrate Navigation Architecture Component in your app in different use cases",
            dimension = "200 г",
            price = 4444
        )

        5 -> ProductItemData(
            id = "0",
            imageUrl = "https://skill-branch.ru/img/mail/bot/android-category.png",
            title = "Optimizing Android ViewModel",
            imageId = R.drawable.pitm_image_7,
            description = "Initialization, passing arguments",
            dimension = "бут",
            price = 555544
        )
        else -> ProductItemData(
            id = "0",
            imageUrl = "https://skill-branch.ru/img/mail/bot/android-category.png",
            imageId = R.drawable.pitm_image_6,
            title = "Drawing a rounded ",
            description = "Let’s say that we need to draw a **rounded** corner background on text, supporting the following cases",
            dimension = "200 г",
            price = 555555
        )
    }
}.toList()

