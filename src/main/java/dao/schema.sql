/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Jarrad Marshall
 * Created: 23/08/2023
 */

CREATE TABLE IF NOT EXISTS Products (
    ProductID INTEGER PRIMARY KEY,
    ProductName varchar(255) NOT NULL,
    Description varchar(255),
    Category varchar(255) NOT NULL,
    ListPrice numeric(10,2) NOT NULL,
    QuantityInStock integer NOT NULL DEFAULT '0',
    CONSTRAINT CHK_NotNegative CHECK((ListPrice >=0) AND (QuantityInStock >=0))   
);

CREATE TABLE  IF NOT EXISTS Customers (
    CustomerID integer AUTO_INCREMENT (1000) PRIMARY KEY,
    Username varchar(255) NOT NULL UNIQUE,
    FirstName varchar(255) NOT NULL,
    Surname varchar(255) NOT NULL,
    Password varchar(255) NOT NULL,
    EmailAddress varchar(255) NOT NULL UNIQUE,
    ShippingAddress varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Sales (
    SaleID integer AUTO_INCREMENT (10000) PRIMARY KEY,
    Date DATETIME NOT NULL,
    CustomerID integer,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
    Status varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS SaleItems (
    ProductID integer,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
    SaleID integer, 
    FOREIGN KEY (SaleID) REFERENCES Sales(SalesID),
    CONSTRAINT PK_SaleItem PRIMARY KEY (SaleID, ProductID),
    QuantityPurchased integer NOT NULL,
    SalePrice numeric(10,2) NOT NULL
);



