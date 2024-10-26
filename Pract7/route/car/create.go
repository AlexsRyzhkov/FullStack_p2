package car

import (
	"github.com/labstack/echo/v4"
	"github/pract7/db"
	"github/pract7/model"
	"net/http"
)

type createRequest struct {
	Car struct {
		Name string `json:"name"`
	} `json:"car"`
}

func Create(c echo.Context) error {
	var req createRequest
	err := c.Bind(&req)
	if err != nil {
		return c.String(http.StatusBadRequest, "bad request")
	}
	
	if req.Car.Name == "" {
		return c.String(http.StatusBadRequest, "Имя не должно быть пустым")
	}
	
	reuslt := db.Conn.Create(&model.Car{Name: req.Car.Name})
	
	if reuslt.Error != nil {
		return c.String(http.StatusBadRequest, "Не удалось создать машину")
	}
	
	return c.JSON(http.StatusCreated, req)
}
