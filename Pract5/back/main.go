package main

import (
	"github.com/google/uuid"
	"github.com/labstack/echo/v4"
	"io"
	"log"
	"net/http"
	"os"
)

func main() {
	e := echo.New()

	e.GET("/files", func(c echo.Context) error {
		dirPath := "./files"

		files, err := os.ReadDir(dirPath)
		if err != nil {
			log.Fatal(err)
		}

		response := make([]string, 0)

		for _, file := range files {
			response = append(response, file.Name())
		}

		return c.JSON(http.StatusOK, response)
	})

	e.GET("/files/:name", func(c echo.Context) error {
		fileName := c.Param("name")

		return c.File("./files/" + fileName)
	})

	e.POST("/upload", func(c echo.Context) error {
		file, err := c.FormFile("file")

		if err != nil {
			return c.String(http.StatusBadRequest, "Ошибка загрузки файла")
		}
		uuid4 := uuid.New()

		src, err := file.Open()
		defer src.Close()
		dst, err := os.Create("./files/" + uuid4.String() + "_" + file.Filename)
		defer dst.Close()

		if _, err = io.Copy(dst, src); err != nil {
			return err
		}

		return c.String(http.StatusOK, "ok")
	})

	e.Logger.Fatal(e.Start(":6000"))
}
