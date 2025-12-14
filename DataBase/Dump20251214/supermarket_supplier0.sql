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
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `Supplier_ID` char(5) NOT NULL,
  `FName` varchar(20) DEFAULT NULL,
  `LName` varchar(50) DEFAULT NULL,
  `Contact_NO` char(10) DEFAULT NULL,
  `Distributor_Company` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Supplier_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES ('SP001','Rajitha','Jayawardena','0771234501','Lanka Food Distributors'),('SP002','Kumara','Wijesuriya','0772345612','Fresh Dairy Supplies Ltd'),('SP003','Amal','Dissanayake','0773456723','Ceylon Rice Exporters'),('SP004','Samantha','Perera','0774567834','Prime Meat Suppliers'),('SP005','Chandrika','Fernando','0775678945','Quality Cheese Imports'),('SP006','Nihal','Silva','0776789056','Beverage Wholesalers Co'),('SP007','Amara','Rajapakse','0777890167','Garden Fresh Vegetables'),('SP008','Bandula','Gunawardena','0778901278','Fruit Paradise Suppliers'),('SP009','Chandra','Wickramasinghe','0779012389','Golden Harvest Oils'),('SP010','Dineth','Samaraweera','0770123490','Sweet Success Bakery'),('SP011','Erandika','Mendis','0771112234','Ocean Fresh Seafood'),('SP012','Fathima','Hasan','0772223345','Tropical Beverages Ltd'),('SP013','Gamini','Rodrigo','0773334456','Snack Masters Pvt Ltd'),('SP014','Hasini','Bandara','0774445567','Home Essentials Supplies'),('SP015','Indika','Pathirana','0775556678','Frozen Foods Lanka'),('SP016','Janaki','Amarasinghe','0776667789','Grain Traders Association'),('SP017','Kamal','Ranasinghe','0777778890','Condiment Specialists'),('SP018','Lakshmi','Herath','0778889901','Dairy Excellence Pvt Ltd'),('SP019','Mahesh','Gamage','0779990012','Organic Produce Co'),('SP020','Nadeeka','Seneviratne','0770001123','Household Products Direct'),('SP021','Oshadha','Cooray','0771122335','Beverage Central'),('SP022','Priyantha','Rathnayake','0772233446','Meat Masters Lanka'),('SP023','Ranjani','Liyanage','0773344557','Fresh Bakes Suppliers'),('SP024','Saman','De Silva','0774455668','Seafood Specialties'),('SP025','Tharuka','Kumarasinghe','0775566779','Ceylon Tea Exporters'),('SP026','Upul','Pieris','0776677880','Coffee Bean Imports'),('SP027','Varuni','Fonseka','0777788991','Frozen Delights Ltd'),('SP028','Wasantha','Weerasinghe','0778899002','Cleaning Solutions Co'),('SP029','Yamuna','Jayasuriya','0779900113','Paper Products Lanka'),('SP030','Zoysa','Abeysekara','0770011224','Water Bottlers Association');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-14 19:30:02
