DROP DATABASE IF EXISTS earn;

CREATE DATABASE earn;

USE earn;


CREATE TABLE User (
    UserID INT NOT NULL AUTO_INCREMENT,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Email VARCHAR(50),
    Address1 VARCHAR(50),
    City VARCHAR(50),
    State VARCHAR(50),
    Zip VARCHAR(50),
    Country VARCHAR(50),
    CreditCardType VARCHAR(50),
    CreditCardNumber VARCHAR(50),
    CreditCardExpirationDate VARCHAR(50),
  
    PRIMARY KEY (UserID)
);
  
CREATE TABLE Invoice(
    InvoiceID INT NOT NULL AUTO_INCREMENT,
    UserID INT NOT NULL,
    InvoiceDate DATETIME NOT NULL DEFAULT '1900-01-01 00:00:00',
    TotalAmount FLOAT NOT NULL DEFAULT '0',
    IsProcessed enum('y','n') DEFAULT NULL,
  
    PRIMARY KEY (InvoiceID),
    FOREIGN KEY (UserID) REFERENCES User (UserID)
);
  
CREATE TABLE LineItem(
    LineItemID INT NOT NULL AUTO_INCREMENT,
    InvoiceID INT NOT NULL DEFAULT '0',
    ProductID INT NOT NULL DEFAULT '0',
    Quantity INT NOT NULL DEFAULT '0',
  
    PRIMARY KEY (LineItemID),
    FOREIGN KEY (InvoiceID) REFERENCES Invoice (InvoiceID)
);
  
CREATE TABLE Product(
    ProductID INT NOT NULL AUTO_INCREMENT,
    ProductCode VARCHAR(10) NOT NULL DEFAULT '',
    ProductDescription VARCHAR(100) NOT NULL DEFAULT '',
    ProductPrice DECIMAL(7,2) NOT NULL DEFAULT '0.00',
  
    PRIMARY KEY (ProductID)
);
  
INSERT INTO Product VALUES 
  ('1', 'w1', 'Earn Your Sleep Outta This World Tee - RED', '25.00'),
  ('2', 'w2', 'Chosen Few', '25.00'),
  ('3', 'w3', 'Made with Aloha tee', '25.00'),
  ('4', 'w4', 'Earn Your Sleep Varsity', '25.00'),
  ('5', 'w5', 'Earn Your Sleep TM', '25.00'),
  ('6', 'w6', 'Matthew 10:28 T-shirt', '25.00'),
  ('7', 'w7', 'Smile no sleep later', '20.00'),
  ('8', 'w8', 'Smile now sleep later', '20.00'),
  ('9', 'w9', 'Orange Antisleep tee', '25.00'),
  ('10', 'w10', 'Earn your sleep athletics', '25.00'),
  ('11', 'w11', 'Sleep Slash', '25.00'),
  ('12', 'w12', 'Lost at Sea', '25.00'),
  ('13', 'w13', 'De La Sleep', '25.00'),
  ('14', 'w14', 'Mt. Fuji', '25.00'),
  ('15', 'w15', 'Prov.18:22 Shirt', '25.00'),
  ('16', 'w16', 'All Star tea', '27.00'),
  ('17', 'w17', 'Joe Rut - Genuine Wood Grained Finish', '25.00');