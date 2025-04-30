package org.example.server.controller

import org.example.server.model.Product
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ApiControllerTest {

    private val controller = ApiController()

    @Test
    fun `test getProducts returns 3 valid products with correct properties`() {
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
    fun `test getProducts returns 3 valid products`() {
        val products = controller.getProducts();
        assertEquals(3, products.size)
        assertEquals("Zapatos", products[0].name)
        assertEquals(50.0, products[0].price)
        assertEquals("Camisa", products[1].name)
        assertEquals("Pantalones", products[2].name)
        assertTrue(products.all { it.id > 0 })

    }


    @Test
    fun `test getProducts returns unique IDs`() {
        val products = controller.getProducts()
        val ids = products.map { it.id }
        assertEquals(3, ids.distinct().size)
        assertFalse(ids.contains(0))
    }

    @Test
    fun `test processPayment calculates total correctly`() {
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
    fun `test getProducts returns expected content`() {
        val products = controller.getProducts()

        val expectedNames = listOf("Zapatos", "Camisa", "Pantalones")
        val expectedPrices = listOf(50.0, 20.0, 30.0)

        for (i in products.indices) {
            assertEquals(expectedNames[i], products[i].name)
            assertEquals(expectedPrices[i], products[i].price)
        }
    }

    @Test
    fun `test processPayment with empty cart returns zero`() {
        val cart = emptyList<Product>()
        val response = controller.processPayment(cart)
        assertEquals("Total: 0.0", response.body)
        assertEquals(200, response.statusCodeValue)
    }

    @Test
    fun `test processPayment with negative price returns correct total`() {
        val cart = listOf(
            Product(1, "Discount", -10.0),
            Product(2, "Item", 30.0)
        )
        val response = controller.processPayment(cart)
        assertEquals("Total: 20.0", response.body)
    }

    @Test
    fun `test processPayment with floating point precision`() {
        val cart = listOf(
            Product(1, "Item A", 10.123),
            Product(2, "Item B", 5.456)
        )
        val response = controller.processPayment(cart)
        assertEquals("Total: 15.579", response.body)
    }

    @Test
    fun `test processPayment with zero price products`() {
        val cart = listOf(
            Product(1, "Proste", 0.0),
            Product(2, "Bardzo proste", 0.0)
        )
        val response = controller.processPayment(cart)
        assertEquals("Total: 0.0", response.body)
    }

    @Test
    fun `test getProducts expected content using loop`() {
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
    fun `test processPayment with large values`() {
        val cart = listOf(
            Product(1, "Big", 1_000_000.0),
            Product(2, "Huge", 2_000_000.0)
        )
        val response = controller.processPayment(cart)
        assertEquals("Total: 3000000.0", response.body)
    }

    @Test
    fun `test processPayment returns consistent response`() {
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
    fun `test product model properties`() {
        val product = Product(99, "Keyboard", 129.99)
        assertEquals(99, product.id)
        assertEquals("Keyboard", product.name)
        assertEquals(129.99, product.price)
    }

    @Test
    fun `test payment with one product`() {
        val cart = listOf(Product(10, "Mouse", 49.99))
        val response = controller.processPayment(cart)
        assertEquals("Total: 49.99", response.body)
        assertEquals(200, response.statusCodeValue)
    }

    @Test
    fun `test payment with decimal edge cases`() {
        val cart = listOf(
            Product(1, "Item", 0.333),
            Product(2, "Item2", 0.667)
        )
        val response = controller.processPayment(cart)
        assertEquals("Total: 1.0", response.body)
    }

    @Test
    fun `test product IDs are sequential`() {
        val products = controller.getProducts()
        assertEquals(1, products[0].id)
        assertEquals(2, products[1].id)
        assertEquals(3, products[2].id)
    }

    @Test
    fun `test no null products in getProducts`() {
        val products = controller.getProducts()
        assertTrue(products.none { it == null })
    }

    @Test
    fun `test product names are not blank`() {
        val products = controller.getProducts()
        assertTrue(products.all { it.name.isNotBlank() })
    }

    @Test
    fun `test product prices are positive or zero`() {
        val products = controller.getProducts()
        assertTrue(products.all { it.price >= 0 })
    }

    @Test
    fun `test multiple carts with same total return same response`() {
        val cart1 = listOf(Product(1, "A", 10.0), Product(2, "B", 20.0))
        val cart2 = listOf(Product(3, "C", 15.0), Product(4, "D", 15.0))

        val response1 = controller.processPayment(cart1)
        val response2 = controller.processPayment(cart2)

        assertEquals(response1.body, response2.body)
        assertEquals(response1.statusCodeValue, response2.statusCodeValue)
    }

    @Test
    fun `test empty product name is invalid`() {
        val product = Product(99, "", 20.0)
        assertEquals("", product.name)
        assertTrue(product.name.isBlank())
    }

    @Test
    fun `test floating point rounding behavior`() {
        val cart = listOf(Product(1, "A", 0.1), Product(2, "B", 0.2))
        val response = controller.processPayment(cart)
        assertEquals("Total: 0.30000000000000004", response.body)
    }

    @Test
    fun `test extremely small price`() {
        val cart = listOf(Product(1, "Tiny", 0.000001))
        val response = controller.processPayment(cart)
        assertTrue(response.body!!.startsWith("Total"))

    }
}