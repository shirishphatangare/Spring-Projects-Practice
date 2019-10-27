/* Dumping database structure for spring_bank_app_db*/
/*DROP SCHEMA IF EXISTS `spring_bank_app_db`;*/
CREATE SCHEMA IF NOT EXISTS `spring_bank_app_db`;
USE `spring_bank_app_db`;


/* Dumping structure for table spring_bank_app_db.bank_account_details*/
/*DROP TABLE IF EXISTS `bank_account_details`;*/
CREATE TABLE IF NOT EXISTS `bank_account_details` (
  `ACCOUNT_ID` int(10) NOT NULL AUTO_INCREMENT,
  `BALANCE_AMOUNT` float NOT NULL,
  `LAST_TRANSACTION_TS` datetime NOT NULL,
  PRIMARY KEY (`ACCOUNT_ID`)
);


/* Dumping structure for table spring_bank_app_db.fixed_deposit_details*/
/*DROP TABLE IF EXISTS `fixed_deposit_details`;*/
CREATE TABLE IF NOT EXISTS `fixed_deposit_details` (
  `FIXED_DEPOSIT_ID` int(10) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_ID` int(10) NOT NULL,
  `FD_CREATION_DATE` date NOT NULL,
  `AMOUNT` int(10) NOT NULL,
  `TENURE` int(10) NOT NULL,
  `ACTIVE` char(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`FIXED_DEPOSIT_ID`),
  FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `bank_account_details` (`ACCOUNT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
