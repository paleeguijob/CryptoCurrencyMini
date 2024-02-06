package realaof.realhon.realha.cryptocurrencymini.linemanwongnailivecode

class Cart {
    val items: MutableList<LineItem> = mutableListOf()
    val promotions: MutableList<Promotion> = mutableListOf()

    var percentageDiscount = 0.0

    fun getTotalPrice(): Double {
        if (promotions.isNotEmpty()) getSumPromotionDiscount()

        return getSumItemsPrice() - getSumItemAndDiscountByPercentage(percentageDiscount)
    }

    private fun getSumItemsPrice(): Double {
        return items.sumOf { it.price * it.quantity }
    }

    private fun getSumItemAndDiscountByPercentage(percentage: Double): Double {
        return getSumItemsPrice() * percentage
    }

    private fun getSumPromotionDiscount() {
        val itemsDiscount: MutableList<LineItem> = mutableListOf()
        promotions.map { promotion ->
            items.filter { it.name == promotion.name }.map {
                itemsDiscount.add(it.copy(name = it.name, price = it.price - promotion.discount))
            }
        }

        itemsDiscount.map { item ->
            val removeItem = items.find { find ->
                find.name == item.name
            }
            items.remove(removeItem)
        }
        items.addAll(itemsDiscount)
    }
}

data class LineItem(
    val name: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0
)

data class Promotion(
    val name: String = "",
    val discount: Double = 0.0
)