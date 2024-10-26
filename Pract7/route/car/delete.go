package car

import (
	"github.com/labstack/echo/v4"
	"github/pract7/db"
	"github/pract7/model"
	"net/http"
)

type requestDelete struct {
	ID uint `query:"id"`
}

func Delete(c echo.Context) error {
	var req requestDelete
	err := c.Bind(&req)
	if err != nil {
		return c.String(http.StatusBadRequest, "bad request")
	}
	
	result := db.Conn.Delete(&model.Car{ID: req.ID})
	
	if result.Error != nil {
		return c.String(http.StatusBadRequest, "Не удалось удалить машину")
	}
	
	return c.JSON(http.StatusOK, "Успешно удалено")
}
