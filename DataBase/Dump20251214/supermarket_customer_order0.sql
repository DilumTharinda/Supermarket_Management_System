-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: supermarket
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer_order`
--

DROP TABLE IF EXISTS `customer_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_order` (
  `Order_ID` char(5) NOT NULL,
  `Staff_ID` char(5) DEFAULT NULL,
  `Item_ID` varchar(25) DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  `Customer_ID` char(5) DEFAULT NULL,
  `Purchase_Date` date DEFAULT NULL,
  `Payment_method` varchar(10) DEFAULT NULL,
  `Total_Price` int DEFAULT NULL,
  PRIMARY KEY (`Order_ID`),
  KEY `Staff_ID` (`Staff_ID`),
  KEY `Customer_ID` (`Customer_ID`),
  KEY `Item_ID` (`Item_ID`),
  CONSTRAINT `customer_order_ibfk_1` FOREIGN KEY (`Staff_ID`) REFERENCES `staff` (`Staff_ID`),
  CONSTRAINT `customer_order_ibfk_2` FOREIGN KEY (`Customer_ID`) REFERENCES `customer` (`Customer_ID`),
  CONSTRAINT `customer_order_ibfk_3` FOREIGN KEY (`Item_ID`) REFERENCES `items` (`Item_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_order`
--

LOCK TABLES `customer_order` WRITE;
/*!40000 ALTER TABLE `customer_order` DISABLE KEYS */;
INSERT INTO `customer_order` VALUES ('OR001','S0002','ITM001',2,'C0001','2024-12-01','Cash',240),('OR002','S0003','ITM002',3,'C0002','2024-12-01','Card',840),('OR003','S0006','ITM003',1,'C0003','2024-12-01','Cash',1500),('OR004','S0009','ITM004',2,'C0004','2024-12-02','Card',1900),('OR005','S0002','ITM005',1,'C0005','2024-12-02','Cash',650),('OR006','S0003','ITM006',4,'C0006','2024-12-02','Card',1680),('OR007','S0006','ITM007',3,'C0007','2024-12-03','Cash',540),('OR008','S0009','ITM008',5,'C0008','2024-12-03','Cash',750),('OR009','S0012','ITM009',2,'C0009','2024-12-03','Card',960),('OR010','S0014','ITM010',2,'C0010','2024-12-04','Cash',1100),('OR011','S0012','ITM011',3,'C0011','2024-12-04','Card',660),('OR012','S0006','ITM012',2,'C0012','2024-12-04','Cash',1440),('OR013','S0002','ITM013',4,'C0013','2024-12-05','Card',2160),('OR014','S0003','ITM014',5,'C0014','2024-12-05','Cash',1400),('OR015','S0006','ITM015',10,'C0015','2024-12-05','Cash',800),('OR016','S0009','ITM016',2,'C0016','2024-12-06','Card',840),('OR017','S0012','ITM017',1,'C0017','2024-12-06','Cash',680),('OR018','S0014','ITM018',1,'C0018','2024-12-06','Card',1850),('OR019','S0002','ITM019',3,'C0019','2024-12-07','Cash',960),('OR020','S0002','ITM020',4,'C0020','2024-12-07','Cash',960),('OR021','S0006','ITM021',2,'C0021','2024-12-07','Card',320),('OR022','S0012','ITM022',3,'C0022','2024-12-08','Cash',420),('OR023','S0003','ITM023',4,'C0023','2024-12-08','Card',1520),('OR024','S0009','ITM024',2,'C0024','2024-12-08','Cash',1700),('OR025','S0002','ITM025',3,'C0025','2024-12-09','Card',960),('OR026','S0003','ITM026',2,'C0026','2024-12-09','Cash',560),('OR027','S0006','ITM027',1,'C0027','2024-12-09','Card',450),('OR028','S0009','ITM028',2,'C0028','2024-12-10','Cash',1240),('OR029','S0012','ITM029',1,'C0029','2024-12-10','Card',780),('OR030','S0014','ITM030',6,'C0030','2024-12-10','Cash',540);
/*!40000 ALTER TABLE `customer_order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-14 19:30:03
