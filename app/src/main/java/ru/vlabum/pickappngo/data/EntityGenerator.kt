package ru.vlabum.pickappngo.data

import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.data.models.*
import java.util.*
import kotlin.random.Random.Default.nextBoolean

object EntityGenerator {

//    fun generateArticle(article: ArticleItemData): ArticleData = ArticleData(
//        id = article.id,
//        title = article.title,
//        category = article.category,
//        categoryIcon = article.categoryIcon,
//        poster = article.poster,
//        author = User(
//            "${article.id.toInt() % 6}",
//            article.author,
//            article.authorAvatar,
//            lastVisit = Date().add(-1 * (1..7).random(), TimeUnits.DAY),
//            respect = (100..300).random(),
//            rating = (100..200).random()
//        ),
//        commentCount = article.commentCount,
//        likeCount = article.likeCount,
//        readDuration = article.readDuration,
//        date = article.date
//    )

    fun generateCustomerChoiceItems(count: Int): List<ProductItemData> =
        Array(count) { productItems[it % 6] }
            .toList()
            .mapIndexed { index, article ->
                article.copy(
                    id = "$index"
                    //, price = (10..4000).random().toFloat()
                )
            }


//    fun createArticleItem(articleId: String): ArticleItemData {
//        return productItems[articleId.toInt() % 6].copy(
//            id = articleId,
//            commentCount = (10..40).random(),
//            readDuration = (2..10).random(),
//            likeCount = (15..100).random()
//        )
//    }

}

private val productItems = Array(6) {
    when (it) {
        1 -> ProductItemData(
            id = "0",
            imageUrl = "https://skill-branch.ru/img/mail/bot/android-category.png",
            imageId = R.drawable.pitm_hotpng_5,
            title = "Architecture Components pitfalls",
            description = "LiveData and the Fragment lifecycle",
            price = 1111f
        )

        2 -> ProductItemData(
            id = "0",
            imageId = R.drawable.pitm_hotpng_4,
            imageUrl = "https://skill-branch.ru/img/mail/bot/android-category.png",
            title = "Using Safe args plugin — current state of affairs",
            description = "Article describing usage of Safe args Gradle plugin with the Navigation Architecture Component and current support for argument types",
            price = 2222f
        )

        3 -> ProductItemData(
            id = "0",
            imageUrl = "https://skill-branch.ru/img/mail/bot/android-category.png",
            imageId = R.drawable.pitm_image_4,
            title = "Observe LiveData from ViewModel in Fragment",
            description = "Google introduced Android architecture components which are basically a collection of libraries that facilitate robust design, testable",
            price = 3333f
        )

        4 -> ProductItemData(
            id = "0",
            imageUrl = "https://skill-branch.ru/img/mail/bot/android-category.png",
            imageId = R.drawable.pitm_image_6,
            title = "The New Android In-App Navigation",
            description = "How to integrate Navigation Architecture Component in your app in different use cases",
            price = 4444f

        )

        5 -> ProductItemData(
            id = "0",
            imageUrl = "https://skill-branch.ru/img/mail/bot/android-category.png",
            title = "Optimizing Android ViewModel with Lifecycle 2.2.0",
            imageId = R.drawable.pitm_image_7,
            description = "Initialization, passing arguments, and saved state",
            price = 5555.44f
        )
        else -> ProductItemData(
            id = "0",
            imageUrl = "https://skill-branch.ru/img/mail/bot/android-category.png",
            imageId = R.drawable.pitm_image_6,
            title = "Drawing a rounded corner background on text",
            description = "Let’s say that we need to draw a **rounded** corner background on text, supporting the following cases",
            price = 5555.55f
        )
    }
}.toList()

