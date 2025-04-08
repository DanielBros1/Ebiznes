package database

import (
	// import models from my models package
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

var DB *gorm.DB

func Connect() {
	db, err := gorm.Open(sqlite.Open("go.db"), &gorm.Config{})
	if err != nil {
		panic("failed to connect database")
	}
	DB = db
}
