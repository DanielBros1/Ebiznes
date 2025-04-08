package models

import "gorm.io/gorm"

type Cart struct {
	gorm.Model
	UserID uint
	Items  []Product `gorm:"many2many:cart_products;"`
}
