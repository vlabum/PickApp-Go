package ru.vlabum.pickappngo.data

import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.data.models.*
import ru.vlabum.pickappngo.data.models.CategoryItemData

object EntityGenerator {

    fun generateCustomerChoiceItems(count: Int): List<ProductItemData> = productItems
//        Array(count) { productItems[it % 6] }
//            .toList()
//            .mapIndexed { index, article ->
//                article.copy(
//                    id = "$index"
//                )
//            }

    fun getCategories(): List<CategoryItemData> = categoryItemData
}

private val categoryItemData = Array(size = 4) {
    when (it) {
        1 -> CategoryItemData(
            id = 1,
            idStr = "vegetable",
            title = "Овощи, фрукты, ягоды, зелень, грибы",
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
            title = "Мясо, птица, колбасы, деликатесы",
            imageUrl = R.drawable.cat_meat
        )
        else -> CategoryItemData(
            id = 4,
            idStr = "grocery",
            title = "Макароны, крупы, специи",
            imageUrl = R.drawable.cat_grocery
        )
    }
}.toList()

private val productItems = Array(10) {
    when (it) {
        1 -> ProductItemData(
            id = "1",
            idCategory = "1",
            imageUrl = "",
            imageId = R.drawable.draft_gryby,
            title = "Грибы шампиньоны 250 г лоток",
            price = 7990
        )
        2 -> ProductItemData(
            id = "2",
            idCategory = "1",
            imageUrl = "",
            imageId = R.drawable.draft_gryby_eko,
            title = "Шампиньоны ЕКО 400 г",
            price = 13490
        )
        3 -> ProductItemData(
            id = "3",
            idCategory = "1",
            imageUrl = "",
            imageId = R.drawable.draft_tomaty_sliva,
            title = "Томаты сливовидные 600 г Эко культура",
            price = 15000
        )
        4 -> ProductItemData(
            id = "4",
            idCategory = "1",
            imageUrl = "",
            imageId = R.drawable.draft_ukrop,
            title = "Укроп свежий 30 г подложка Лето",
            price = 2990
        )
        5 -> ProductItemData(
            id = "5",
            idCategory = "1",
            imageUrl = "",
            imageId = R.drawable.draft_luk_zel,
            title = "Лук зелёный 50г подложка Лето",
            price = 2990
        )
        6 -> ProductItemData(
            id = "6",
            idCategory = "1",
            imageUrl = "",
            imageId = R.drawable.draft_luk_zel,
            title = "Салат Белая дача пряная руккола 65г",
            price = 15000
        )
        7 -> ProductItemData(
            id = "7",
            idCategory = "1",
            imageUrl = "",
            imageId = R.drawable.draft_tomaty_cherri,
            title = "Томаты Черри 250г коктейль подложка Эко",
            price = 7990
        )
        8 -> ProductItemData(
            id = "8",
            idCategory = "1",
            imageUrl = "",
            imageId = R.drawable.draft_petrushka,
            title = "Петрушка свежая 30г подложка лето",
            price = 2990
        )
        9 -> ProductItemData(
            id = "9",
            idCategory = "1",
            imageUrl = "",
            imageId = R.drawable.draft_morkov_bel,
            title = "Морковь резаная Белая дача 250 г",
            price = 4990
        )
        10 -> ProductItemData(
            id = "10",
            idCategory = "1",
            imageUrl = "",
            imageId = R.drawable.draft_banan_arkada,
            title = "Банан Аркада 100г чипсы п\\п",
            price = 11400
        )

        11 -> ProductItemData(
            id = "11",
            idCategory = "2",
            imageUrl = "",
            imageId = R.drawable.draft_milk_parmalat,
            title = "Молоко Parmalat ультрапатеризованное",
            price = 8290
        )
        12 -> ProductItemData(
            id = "12",
            idCategory = "2",
            imageUrl = "",
            imageId = R.drawable.draft_milk_domik,
            title = "Молоко Домик в деревне отборное",
            price = 6990
        )
        13 -> ProductItemData(
            id = "13",
            idCategory = "2",
            imageUrl = "",
            imageId = R.drawable.draft_slivki_domik,
            title = "Сливки Домик в деревне 10% 480 г",
            price = 11990
        )
        14 -> ProductItemData(
            id = "14",
            idCategory = "2",
            imageUrl = "",
            imageId = R.drawable.draft_danissimo,
            title = "Десерт Даниссимо фантазия 105 г",
            price = 3990
        )
        15 -> ProductItemData(
            id = "15",
            idCategory = "2",
            imageUrl = "",
            imageId = R.drawable.draft_savushkin,
            title = "Йогурт ТМ \"Савушкин\"",
            price = 3990
        )
        16 -> ProductItemData(
            id = "16",
            idCategory = "2",
            imageUrl = "",
            imageId = R.drawable.draft_savushkin,
            title = "Йогурт Греческий 2,0% ТМ \"Савушкин\"",
            price = 2290
        )
        17 -> ProductItemData(
            id = "17",
            idCategory = "2",
            imageUrl = "",
            imageId = R.drawable.draft_eggs,
            title = "Яйца куриные пищевые столовые",
            price = 8990
        )
        18 -> ProductItemData(
            id = "18",
            idCategory = "2",
            imageUrl = "",
            imageId = R.drawable.draft_milk_domik,
            title = "Кефир Асеньевская ферма 0,9л 3,2% пл/б",
            price = 10500
        )
        19 -> ProductItemData(
            id = "19",
            idCategory = "2",
            imageUrl = "",
            imageId = R.drawable.draft_ryazhenka,
            title = "Ряженка Авида 1000 г 3,2% ТП",
            price = 6990
        )
        20 -> ProductItemData(
            id = "20",
            idCategory = "2",
            imageUrl = "",
            imageId = R.drawable.draft_smetana_prosto,
            title = "Сметана Простоквашино 20%",
            price = 9990
        )
        21 -> ProductItemData(
            id = "21",
            idCategory = "2",
            imageUrl = "",
            imageId = R.drawable.draft_smetana_domic,
            title = "Сметана Домик в деревне 20%",
            price = 6390
        )


        22 -> ProductItemData(
            id = "22",
            idCategory = "3",
            imageUrl = "",
            imageId = R.drawable.draft_sosiski2,
            title = "Сосиски Слчивочные Клинский МК 470 г",
            price = 15990
        )
        23 -> ProductItemData(
            id = "23",
            idCategory = "3",
            imageUrl = "",
            imageId = R.drawable.draft_kurica,
            title = "Тушка ЦБ Петелинка потраш 1 с подложкой",
            price = 13990
        )
        24 -> ProductItemData(
            id = "24",
            idCategory = "3",
            imageUrl = "",
            imageId = R.drawable.draft_kurica,
            title = "Стейк грудки индейки 500г Индилайт лоток",
            price = 13990
        )
        25 -> ProductItemData(
            id = "25",
            idCategory = "3",
            imageUrl = "",
            imageId = R.drawable.draft_doctorsk,
            title = "Колбаса вареная Доксторская ТМ Останкино",
            price = 19900
        )
        26 -> ProductItemData(
            id = "26",
            idCategory = "3",
            imageUrl = "",
            imageId = R.drawable.draft_holodec,
            title = "Холодец Ремит 400 г Деревенский пл/б",
            price = 20000
        )
        27 -> ProductItemData(
            id = "27",
            idCategory = "3",
            imageUrl = "",
            imageId = R.drawable.draft_kotleta,
            title = "Котлеты Мираторг 400 г из говядины охл.",
            price = 25990
        )
        28 -> ProductItemData(
            id = "28",
            idCategory = "3",
            imageUrl = "",
            imageId = R.drawable.draft_sosiski_papa,
            title = "Сосиски Папа Может! 350 г",
            price = 14700
        )
        29 -> ProductItemData(
            id = "29",
            idCategory = "3",
            imageUrl = "",
            imageId = R.drawable.draft_carbonad,
            title = "Карбонад Юбилейный Останкино нарезка",
            price = 15990
        )
        30 -> ProductItemData(
            id = "30",
            idCategory = "3",
            imageUrl = "",
            imageId = R.drawable.draft_chevapchi,
            title = "Чевапчичи Ближние горки 300 г из свинины охл.",
            price = 13900
        )
        31 -> ProductItemData(
            id = "31",
            idCategory = "3",
            imageUrl = "",
            imageId = R.drawable.draft_colbasa_braun,
            title = "Колбаса Егорьевская КГФ Брауншвейгская",
            price = 14990
        )

        32 -> ProductItemData(
            id = "32",
            idCategory = "4",
            imageId = R.drawable.draft_muka_makfa,
            title = "Мука MAKFA высший сорт 2кг",
            price = 8500
        )
        33 -> ProductItemData(
            id = "33",
            idCategory = "4",
            imageId = R.drawable.draft_muka_ryazan,
            title = "Мука Рязаночка 2кг Экстра пшеничная п/п",
            price = 9990
        )
        34 -> ProductItemData(
            id = "34",
            idCategory = "4",
            imageId = R.drawable.draft_pasta_barilla,
            title = "Паста Barilla Bavette n.13 500 г",
            price = 8990
        )
        35 -> ProductItemData(
            id = "35",
            idCategory = "4",
            imageId = R.drawable.draft_ver_makfa,
            title = "Вермишель MAKFA длинная спагетти 500г",
            price = 4990
        )
        36 -> ProductItemData(
            id = "36",
            idCategory = "4",
            imageId = R.drawable.draft_ver_agro,
            title = "Мак. изделия АиДа гнезда Тальятелли",
            price = 5990
        )
        37 -> ProductItemData(
            id = "37",
            idCategory = "4",
            imageId = R.drawable.draft_nestle_gold,
            title = "Готовый завтрак Nestle Gold Honey Nut",
            price = 17490
        )
        38 -> ProductItemData(
            id = "38",
            idCategory = "4",
            imageId = R.drawable.draft_musli,
            title = "Мюсли ОГО! запечённые с орехом 375 г",
            price = 9790
        )
        39 -> ProductItemData(
            id = "39",
            idCategory = "4",
            imageId = R.drawable.draft_ovs,
            title = "Овсяные хлопья Русский продукт Геркулес 500 г",
            price = 5890
        )
        else -> ProductItemData(
            id = "40",
            idCategory = "4",
            imageId = R.drawable.draft_sun_oil,
            title = "Масло подсолнечное Золотая семечка 1л",
            price = 9990
        )


    }
}.toList()

