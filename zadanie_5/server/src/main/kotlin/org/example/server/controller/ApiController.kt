package org.example.server.controller

import org.example.server.model.Product
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@RestController
class ApiController {

    @GetMapping("/products")
    fun getProducts(): List<Product> {
        return listOf(
            Product(1, "Zapatos", 50.0),
            Product(2, "Camisa", 20.0),
            Product(3, "Pantalones", 30.0)
        )
    }

    @PostMapping("/payment")
    fun processPayment(@RequestBody cart: List<Product>): ResponseEntity<String> {
        val total = cart.sumOf { it.price }
        return ResponseEntity.ok("Total: $total")
    }

    // CORS configuration
//    @Bean
//    fun corsConfig(): WebMvcConfigurer {
//        return object : WebMvcConfigurer {
//            override fun addCorsMappings(registry: CorsRegistry) {
//                registry.addMapping("/**")
//                    .allowedOrigins("http://localhost:3000")
//                    .allowedMethods("GET", "POST", "PUT", "DELETE")
//            }
//        }
//    }


    @Bean
    fun corsConfig(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("*")  // Tymczasowo dla testów
                    .allowedMethods("*")
                    .allowedHeaders("*")
            }
        }
    }
}