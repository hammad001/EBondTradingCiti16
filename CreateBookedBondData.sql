USE mydatabase;

Drop table BookedBond;

CREATE TABLE BookedBond(
OrderId int AUTO_INCREMENT PRIMARY KEY ,
BondId int,
BuySell char,
Quantity int,
PurchaseDate date DEFAULT '2016-08-27',
FOREIGN KEY (BondID) REFERENCES EBOND(BondID)
);
 
 

LOAD DATA local INFILE 'C:\\Users\\Grad41\\Documents\\booked_bond_data.csv' 
INTO TABLE mydatabase.bookedbond 
FIELDS
	ENCLOSED BY '"'
	TERMINATED BY ',' 
LINES 
	TERMINATED BY '\r\n'
IGNORE 1 lines
(BondId, BuySell, Quantity);