package models

import "gorm.io/gorm"

type Category struct {
	gorm.Model
	name     string
	Products []Product
}
