package main

import (
	"github.com/labstack/echo/v4"
	"github/pract7/db"
	"github/pract7/route/car"
)

func main() {
	
	e := echo.New()
	e.GET("/cars", car.GetAll)
	e.GET("/cars/stream", car.GetStream)
	
	e.POST("/cars", car.Create)
	e.PUT("/cars/:id", car.Update)
	e.DELETE("/cars/:id", car.Delete)
	
	db.InitDB()
	e.Logger.Fatal(e.Start(":8080"))
}
