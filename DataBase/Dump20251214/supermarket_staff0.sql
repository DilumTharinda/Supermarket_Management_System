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
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `Staff_ID` char(5) NOT NULL,
  `FName` varchar(20) DEFAULT NULL,
  `LName` varchar(50) DEFAULT NULL,
  `Contact_NO` char(10) DEFAULT NULL,
  `Address` varchar(50) DEFAULT NULL,
  `Position` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Staff_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES ('S0001','Sunil','Fernando','0711234567','45 Galle Rd, Colombo','Manager'),('S0002','Nimal','Silva','0712345678','67 Kandy Rd, Kandy','Cashier'),('S0003','Kamala','Perera','0713456789','89 Main St, Galle','Cashier'),('S0004','Ruwan','Jayasinghe','0714567890','12 Park Ave, Negombo','Stock Clerk'),('S0005','Dilina','Wickramasinghe','0715678901','34 Beach Rd, Matara','Supervisor'),('S0006','Chaminda','Gunasekara','0716789012','56 Lake Rd, Jaffna','Cashier'),('S0007','Sanduni','Rodrigo','0717890123','78 Hill St, Anuradhapura','Stock Clerk'),('S0008','Tharindu','Mendis','0718901234','90 River Ave, Trincomalee','Security'),('S0009','Ishara','Bandara','0719012345','23 Valley Rd, Kurunegala','Cashier'),('S0010','Prasad','Dissanayake','0710123456','45 Garden Ln, Ratnapura','Stock Clerk'),('S0011','Niroshi','Rajapakse','0711112233','67 Forest Dr, Batticaloa','Manager'),('S0012','Udara','Samaraweera','0712223344','89 Ocean Blvd, Badulla','Cashier'),('S0013','Anusha','Wijesinghe','0713334455','12 Palm St, Ampara','Stock Clerk'),('S0014','Kasun','Amarasinghe','0714445566','34 Sunset Ave, Polonnaruwa','Cashier'),('S0015','Madhavi','Jayasuriya','0715556677','56 Maple Rd, Kalutara','Supervisor'),('S0031','Niranjana','Senanayake','0711223344','12 Independence Ave, Colombo 7','HR'),('S0032','Kavinda','Wijesooriya','0712334455','45 Duplication Rd, Colombo 4','IT'),('S0033','Lahiru','Priyankara','0713445566','78 Baseline Rd, Colombo 9','Storekeeper'),('S0034','Menaka','Jayawardena','0714556677','23 Flower Rd, Colombo 3','Storekeeper');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
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
