package routes

import (
	"github.com/labstack/echo/v4"
	"zadanie_4/controllers"
)

func RegisterRoutes(e *echo.Echo) {
	e.GET("/products", controllers.GetProducts)
	e.POST("/products", controllers.CreateProduct)
	e.PUT("/products/:id", controllers.UpdateProduct)
	e.DELETE("/products/:id", controllers.DeleteProduct)

	e.GET("/cart", controllers.GetCard)

	// SCOPE
	e.GET("/products/cheap", controllers.GetCheapProducts)
}
