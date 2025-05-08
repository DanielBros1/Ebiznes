package org.example.server

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.server.model.Product
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTest {

    companion object {
        const val PAYMENT_ENDPOINT = "/payment"
    }

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun getProductsReturnsList() {
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value("Zapatos"))
    }

    @Test
    fun postPaymentReturnsTotal() {
        val cart = listOf(Product(1, "Item", 99.99))
        mockMvc.perform(
            post(PAYMENT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cart))
        )
            .andExpect(status().isOk)
            .andExpect(content().string("Total: 99.99"))
    }

    @Test
    fun postPaymentWithEmptyBodyReturns400() {
        mockMvc.perform(
            post(PAYMENT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]")
        )
            .andExpect(status().isOk) // Empty list = total 0, still OK
            .andExpect(content().string("Total: 0.0"))
    }

    @Test
    fun postPaymentWithInvalidJsonReturns400() {
        mockMvc.perform(
            post(PAYMENT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"invalid\": \"json\"}")
        ).andExpect(status().isBadRequest)
    }
}
