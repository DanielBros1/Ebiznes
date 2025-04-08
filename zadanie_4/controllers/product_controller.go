package controllers

import (
	"github.com/labstack/echo/v4"
	"net/http"
	"zadanie_4/database"
	"zadanie_4/models"
)

// ALL CRUD OPERATIONS FOR PRODUCTS

func CreateProduct(c echo.Context) error {
	product := new(models.Product)
	if err := c.Bind(product); err != nil {
		return err
	}
	database.DB.Create(&product)
	return c.JSON(http.StatusCreated, product)
}

func GetProducts(c echo.Context) error {
	var products []models.Product
	database.DB.Preload("Category").Find(&products)
	return c.JSON(http.StatusOK, products)
}

func UpdateProduct(c echo.Context) error {
	id := c.Param("id")
	var product models.Product
	if err := database.DB.First(&product, id).Error; err != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"message": "Product not found"})
	}

	if err := c.Bind(&product); err != nil {
		return err
	}

	database.DB.Save(&product)
	return c.JSON(http.StatusOK, product)
}

func DeleteProduct(c echo.Context) error {
	id := c.Param("id")
	if err := database.DB.Delete(&models.Product{}, id).Error; err != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"message": "Product not found"})
	}
	return c.JSON(http.StatusOK, map[string]string{"message": "Product deleted"})
}

// SCOPE
func GetCheapProducts(c echo.Context) error {
	price := 100
	var products []models.Product

	if err := database.DB.Scopes(models.PriceLessThan(float64(price))).Preload("Category").Find(&products).Error; err != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"message": "No products found"})
	}

	return c.JSON(http.StatusOK, products)
}
