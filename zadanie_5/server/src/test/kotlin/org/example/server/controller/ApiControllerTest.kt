package org.example.server.controller

import org.example.server.model.Product
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ApiControllerTest {

    private val controller = ApiController()

    @Test
    fun testGetProductsReturns3ValidProductsWithCorrectProperties() {
        val products = controller.getProducts()
        assertEquals(3, products.size)
        val product1 = products[0]
        val product2 = products[1]
        val product3 = products[2]

        assertEquals("Zapatos", product1.name)
        assertEquals(50.0, product1.price)
        assertTrue(product1.id > 0)

        assertEquals("Camisa", product2.name)
        assertEquals(20.0, product2.price)
        assertTrue(product2.id > 0)

        assertEquals("Pantalones", product3.name)
        assertEquals(30.0, product3.price)
        assertTrue(product3.id > 0)
    }

    @Test
    fun testGetProductsReturns3ValidProducts() {
        val products = controller.getProducts();
        assertEquals(3, products.size)
        assertEquals("Zapatos", products[0].name)
        assertEquals(50.0, products[0].price)
        assertEquals("Camisa", products[1].name)
        assertEquals("Pantalones", products[2].name)
        assertTrue(products.all { it.id > 0 })

    }


    @Test
    fun testGetProductsReturnsUniqueIDs() {
        val products = controller.getProducts()
        val ids = products.map { it.id }
        assertEquals(3, ids.distinct().size)
        assertFalse(ids.contains(0))
    }

    @Test
    fun testProcessPaymentCalculatesTotalCorrectly() {
        val cart = listOf(
            Product(1, "Test1", 10.0),
            Product(2, "Test2", 15.5)
        )
        val response = controller.processPayment(cart)
        assertEquals("Total: 25.5", response.body)
        assertEquals(200, response.statusCodeValue)
        assertNotNull(response)
    }

    @Test
    fun testGetProductsReturnsExpectedContent() {
        val products = controller.getProducts()

        val expectedNames = listOf("Zapatos", "Camisa", "Pantalones")
        val expectedPrices = listOf(50.0, 20.0, 30.0)

        for (i in products.indices) {
            assertEquals(expectedNames[i], products[i].name)
            assertEquals(expectedPrices[i], products[i].price)
        }
    }

    @Test
    fun testProcessPaymentWithEmptyCartReturnsZero() {
        val cart = emptyList<Product>()
        val response = controller.processPayment(cart)
        assertEquals("Total: 0.0", response.body)
        assertEquals(200, response.statusCodeValue)
    }

    @Test
    fun testProcessPaymentWithNegativePriceReturnsCorrectTotal() {
        val cart = listOf(
            Product(1, "Discount", -10.0),
            Product(2, "Item", 30.0)
        )
        val response = controller.processPayment(cart)
        assertEquals("Total: 20.0", response.body)
    }

    @Test
    fun testProcessPaymentWithFloatingPointPrecision() {
        val cart = listOf(
            Product(1, "Item A", 10.123),
            Product(2, "Item B", 5.456)
        )
        val response = controller.processPayment(cart)
        assertEquals("Total: 15.579", response.body)
    }

    @Test
    fun testProcessPaymentWithZeroPriceProducts() {
        val cart = listOf(
            Product(1, "Proste", 0.0),
            Product(2, "Bardzo proste", 0.0)
        )
        val response = controller.processPayment(cart)
        assertEquals("Total: 0.0", response.body)
    }

    @Test
    fun testGetProductsExpectedContentUsingLoop() {
        val products = controller.getProducts()
        val expectedNames = listOf("Zapatos", "Camisa", "Pantalones")
        val expectedPrices = listOf(50.0, 20.0, 30.0)

        for (i in products.indices) {
            assertEquals(expectedNames[i], products[i].name)
            assertEquals(expectedPrices[i], products[i].price)
            assertTrue(products[i].id > 0)
        }
    }

    @Test
    fun testProcessPaymentWithLargeValues() {
        val cart = listOf(
            Product(1, "Big", 1_000_000.0),
            Product(2, "Huge", 2_000_000.0)
        )
        val response = controller.processPayment(cart)
        assertEquals("Total: 3000000.0", response.body)
    }

    @Test
    fun testProcessPaymentReturnsConsistentResponse() {
        val cart = listOf(
            Product(1, "X", 1.0),
            Product(2, "Y", 2.0),
            Product(3, "Z", 3.0)
        )
        val response = controller.processPayment(cart)
        assertEquals("Total: 6.0", response.body)
        assertEquals(200, response.statusCodeValue)
        assertNotNull(response.body)
        assertTrue(response.body!!.startsWith("Total"))
    }

    @Test
    fun testProductModelProperties() {
        val product = Product(99, "Keyboard", 129.99)
        assertEquals(99, product.id)
        assertEquals("Keyboard", product.name)
        assertEquals(129.99, product.price)
    }

    @Test
    fun testPaymentWithOneProduct() {
        val cart = listOf(Product(10, "Mouse", 49.99))
        val response = controller.processPayment(cart)
        assertEquals("Total: 49.99", response.body)
        assertEquals(200, response.statusCodeValue)
    }

    @Test
    fun testPaymentWithDecimalEdgeCases() {
        val cart = listOf(
            Product(1, "Item", 0.333),
            Product(2, "Item2", 0.667)
        )
        val response = controller.processPayment(cart)
        assertEquals("Total: 1.0", response.body)
    }

    @Test
    fun testProductIDsAreSequential() {
        val products = controller.getProducts()
        assertEquals(1, products[0].id)
        assertEquals(2, products[1].id)
        assertEquals(3, products[2].id)
    }

    @Test
    fun testNoNullProductsInGetProducts() {
        val products = controller.getProducts()
        assertTrue(products.none { it == null })
    }

    @Test
    fun testProductNamesAreNotBlank() {
        val products = controller.getProducts()
        assertTrue(products.all { it.name.isNotBlank() })
    }

    @Test
    fun testProductPricesArePositiveOrZero() {
        val products = controller.getProducts()
        assertTrue(products.all { it.price >= 0 })
    }

    @Test
    fun testMultipleCartsWithSameTotalReturnSameResponse() {
        val cart1 = listOf(Product(1, "A", 10.0), Product(2, "B", 20.0))
        val cart2 = listOf(Product(3, "C", 15.0), Product(4, "D", 15.0))

        val response1 = controller.processPayment(cart1)
        val response2 = controller.processPayment(cart2)

        assertEquals(response1.body, response2.body)
        assertEquals(response1.statusCodeValue, response2.statusCodeValue)
    }

    @Test
    fun testEmptyProductNameIsInvalid() {
        val product = Product(99, "", 20.0)
        assertEquals("", product.name)
        assertTrue(product.name.isBlank())
    }

    @Test
    fun testFloatingPointRoundingBehavior() {
        val cart = listOf(Product(1, "A", 0.1), Product(2, "B", 0.2))
        val response = controller.processPayment(cart)
        assertEquals("Total: 0.30000000000000004", response.body)
    }

    @Test
    fun testExtremelySmallPrice() {
        val cart = listOf(Product(1, "Tiny", 0.000001))
        val response = controller.processPayment(cart)
        assertTrue(response.body!!.startsWith("Total"))

    }
}