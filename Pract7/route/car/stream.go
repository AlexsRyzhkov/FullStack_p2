package car

import (
	"encoding/json"
	"fmt"
	"github.com/labstack/echo/v4"
	"github/pract7/db"
	"github/pract7/model"
	"net/http"
	"time"
)

func GetStream(c echo.Context) error {
	c.Response().Header().Set(echo.HeaderContentType, echo.MIMEApplicationJSON)
	c.Response().WriteHeader(http.StatusOK)
	
	var cars []model.Car
	db.Conn.Find(&cars)
	
	fmt.Println(cars)
	
	enc := json.NewEncoder(c.Response())
	
	for _, car := range cars {
		if err := enc.Encode(car); err != nil {
			return err
		}
		
		c.Response().Flush()
		time.Sleep(1 * time.Second)
	}
	
	return nil
}
