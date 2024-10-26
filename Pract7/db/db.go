package db

import (
	"github/pract7/model"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

var Conn *gorm.DB

func InitDB() {
	dsn := "host=localhost user=admin password=admin dbname=db port=5435"
	db, _ := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	Conn = db
	
	_ = db.AutoMigrate(&model.Car{})
}
