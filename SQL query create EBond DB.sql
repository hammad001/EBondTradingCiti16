USE mydatabase;

CREATE TABLE EBOND(
BondId int  AUTO_INCREMENT PRIMARY KEY,
ISIN varchar(20),
IssuedBy varchar(50),
IssueDate Date DEFAULT '2000-01-01',
SettlementDays Date DEFAULT '0000-00-02',
FaceValue int DEFAULT 100,
CouponRate double,
CouponFrequency varchar(1),
MaturityDate Date,
LastPrice double,
High double,
Low double,
ChangeInPrice double,
Yeild double,
CreditRating varchar(10),
Currency varchar(5));

LOAD DATA local INFILE 'C:\\Users\\Grad41\\Documents\\bond_data.csv' 
INTO TABLE mydatabase.ebond 
FIELDS
	ENCLOSED BY '"'
	TERMINATED BY ',' 
LINES 
	TERMINATED BY '\r\n'
IGNORE 1 lines
(BondId,IssuedBy,ISIN,CouponRate,@var,High,Low,LastPrice,Yeild,ChangeInPrice,CreditRating,Currency,CouponFrequency)
SET MaturityDate=STR_TO_DATE(@var,'%m/%d/%Y'); 



