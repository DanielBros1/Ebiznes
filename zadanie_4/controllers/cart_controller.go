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
