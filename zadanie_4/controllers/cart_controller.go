package controllers

import (
	"github.com/labstack/echo/v4"
	"net/http"
	"zadanie_4/database"
	"zadanie_4/models"
)

func GetCard(c echo.Context) error {
	var cart models.Cart
	if err := database.DB.First(&cart).Error; err != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"message": "Cart not found"})
	}
	return c.JSON(http.StatusOK, cart)
}

func AddToCart(c echo.Context) error {
	var cart models.Cart
	var product models.Product
	if err := database.DB.First(&product, c.Param("id")).Error; err != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"message": "Product not found"})
	}
	if err := database.DB.First(&cart, c.Param("cart_id")).Error; err != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"message": "Cart not found"})
	}

	err := database.DB.Model(&cart).Association("Products").Append(&product)
	if err != nil {
		return err
	}
	return c.JSON(http.StatusOK, cart)
}
