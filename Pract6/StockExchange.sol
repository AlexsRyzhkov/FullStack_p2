// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract StockExchange {
    struct Stock {
        string name;
        uint256 price;
        address owner;
        bool isAvailable; // Позволяет ли покупать
    }

    mapping(uint256 => Stock) public stocks;
    uint256 public stockCount;
    event LogValue(uint256 value);

    // Создание нового ценной бумаги
    function createStock(string memory _name, uint256 _price) public {
        stockCount++;
        stocks[stockCount] = Stock(_name, _price, msg.sender, true);
    }

    // Покупка ценной бумаги
    function purchaseStock(uint256 _stockId) public payable{
        Stock storage stock = stocks[_stockId];

        emit LogValue(msg.value);

        require(stock.isAvailable, "Stock is not available");
        require(msg.value >= stock.price, "Insufficient funds");

        payable(stock.owner).transfer(stock.price);
        stock.owner = msg.sender;
        stock.isAvailable = false;
    }

    // Обмен ценной бумаги
    function exchangeStock(uint256 _stockId, uint256 _stockId2) public {
        Stock storage stock = stocks[_stockId];
        Stock storage stock1 = stocks[_stockId2];

        address owner = stock.owner;
        stock.owner = stock1.owner;
        stock1.owner = owner;
    }
}