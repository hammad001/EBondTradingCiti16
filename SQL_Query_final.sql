
CREATE TABLE `user` (
  `accountId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`accountId`),
  UNIQUE KEY `loginId_UNIQUE` (`userName`)  
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

ALTER TABLE bookedbond
add column BookedBy int;

ALTER TABLE bookedbond
ADD FOREIGN KEY (BookedBy)
REFERENCES user(accountId);

update bookedbond 
SET BookedBy= FLOOR(7-RAND()*(7-1));

