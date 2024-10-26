package car

import (
	"github.com/labstack/echo/v4"
	"github/pract7/db"
	"github/pract7/model"
	"net/http"
)

type requestUpdate struct {
	ID  uint `query:"id"`
	Car struct {
		Name string `json:"name"`
	} `json:"car"`
}

func Update(c echo.Context) error {
	var req requestUpdate
	err := c.Bind(&req)
	if err != nil {
		return c.String(http.StatusBadRequest, "bad request")
	}
	
	if req.Car.Name == "" {
		return c.String(http.StatusBadRequest, "Имя не должно быть пустым")
	}
	
	car := &model.Car{ID: req.ID, Name: req.Car.Name}
	result := db.Conn.Save(car)
	
	if result.Error != nil {
		return c.String(http.StatusBadRequest, "Не удалось обновить машину")
	}
	
	return c.JSON(http.StatusOK, car)
}
