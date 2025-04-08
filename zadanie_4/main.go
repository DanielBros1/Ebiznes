package main

import (
	"net/http"
	"zadanie_4/database"
	"zadanie_4/models"
	"zadanie_4/routes"

	"github.com/labstack/echo/v4"
)

func main() {
	e := echo.New()

	database.Connect()
	err := database.DB.AutoMigrate(
		&models.Product{},
		&models.Category{},
		&models.Cart{},
		&models.User{},
		&models.Order{},
	)
	if err != nil {
		return
	}

	routes.RegisterRoutes(e)

	e.GET("/", func(c echo.Context) error {
		return c.String(http.StatusOK, "Working")
	})

	e.Logger.Fatal(e.Start(":8080"))
}
