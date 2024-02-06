package realaof.realhon.realha.cryptocurrencymini.linemanwongnailivecode

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CartTest {
    private lateinit var cart: Cart

    @Before
    fun setup() {
        cart = Cart()
    }

    @Test
    fun testCase1_EmptyTotalPrice() {
        cart.items.add(LineItem("Frog Lab", 0.0, 1))

        Assert.assertEquals(0.0, cart.getTotalPrice(), 1.0)
    }

    @Test
    fun testCase2_addItemToCart() {
        cart.items.add(LineItem("StarBuck", 220.0, 1))

        Assert.assertEquals(220.0, cart.getTotalPrice(), 1.0)
    }

    @Test
    fun testCase3_add2ItemToCart() {
        cart.items.add(LineItem("StarBuck", 220.0, 3))
        cart.items.add(LineItem("Amazon", 100.0, 5))

        Assert.assertEquals(1160.0, cart.getTotalPrice(), 1.0)
    }

    @Test
    fun testCase4_percentageDiscount() {
        cart.items.add(LineItem("StarBuck", 220.0, 1))
        cart.percentageDiscount = 0.2

        Assert.assertEquals(176.0, cart.getTotalPrice(), 1.0)
    }

    @Test
    fun testCase5_promotionDiscount1Item() {
        cart.items.add(LineItem("StarBuck", 220.0, 3))
        cart.items.add(LineItem("Amazon", 100.0, 5))

        cart.promotions.add(Promotion("StarBuck", 1.0))

        Assert.assertEquals(1157.0, cart.getTotalPrice(), 1.0)
    }

    @Test
    fun testCase6_promotionDiscountMoreThan1Item() {
        cart.items.add(LineItem("StarBuck", 220.0, 3))
        cart.items.add(LineItem("Amazon", 100.0, 5))

        cart.promotions.add(Promotion("StarBuck", 1.0))
        cart.promotions.add(Promotion("Amazon", 1.0))

        Assert.assertEquals(1152.0, cart.getTotalPrice(), 1.0)
    }

    @Test
    fun testCase7_promotionDiscountAndPercentageDiscount() {
        cart.items.add(LineItem("StarBuck", 220.0, 3))
        cart.items.add(LineItem("Amazon", 100.0, 5))

        cart.promotions.add(Promotion("StarBuck", 1.0))
        cart.promotions.add(Promotion("Amazon", 1.0))

        cart.percentageDiscount = 0.1

        Assert.assertEquals(1036.8, cart.getTotalPrice(), 1.0)
    }
}