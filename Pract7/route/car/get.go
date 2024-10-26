package car

import (
	"github.com/labstack/echo/v4"
	"github/pract7/db"
	"github/pract7/model"
	"net/http"
)

func GetAll(c echo.Context) error {
	var cars []model.Car
	
	db.Conn.Find(&cars)
	
	return c.JSON(http.StatusCreated, cars)
}
