package model

type Car struct {
	ID   uint   `gorm:"primaryKey,column:id" json:"id"`
	Name string `gorm:"column:name" json:"name"`
}

func (Car) TableName() string {
	return "cars"
}
