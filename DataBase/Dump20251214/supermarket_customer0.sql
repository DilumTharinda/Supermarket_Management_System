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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `Customer_ID` char(5) NOT NULL,
  `FName` varchar(20) DEFAULT NULL,
  `LName` varchar(50) DEFAULT NULL,
  `Contact_NO` char(10) DEFAULT NULL,
  `Address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Customer_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('C0001','Kasun','Perera','0771234567','123 Main St, Colombo'),('C0002','Dilini','Silva','0772345678','456 Park Ave, Kandy'),('C0003','Nuwan','Fernando','0773456789','789 Lake Rd, Galle'),('C0004','Sanduni','Jayasinghe','0774567890','321 Hill St, Negombo'),('C0005','Tharindu','Wickramasinghe','0775678901','654 Beach Rd, Matara'),('C0006','Ishara','Gunasekara','0776789012','987 River Ave, Jaffna'),('C0007','Chaminda','Rodrigo','0777890123','147 Valley Rd, Anuradhapura'),('C0008','Nimali','Mendis','0778901234','258 Mountain St, Trincomalee'),('C0009','Ruwan','Bandara','0779012345','369 Garden Ln, Kurunegala'),('C0010','Madhavi','Dissanayake','0770123456','741 Forest Dr, Ratnapura'),('C0011','Gayan','Rajapakse','0771112222','852 Ocean Blvd, Batticaloa'),('C0012','Shanika','Samaraweera','0772223333','963 Palm St, Badulla'),('C0013','Pradeep','Wijesinghe','0773334444','159 Sunset Ave, Ampara'),('C0014','Malini','Amarasinghe','0774445555','357 Maple Rd, Polonnaruwa'),('C0015','Janaka','Jayasuriya','0775556666','468 Oak St, Kalutara'),('C0016','Saduni','Ranasinghe','0776667777','579 Pine Ln, Hambantota'),('C0017','Lakshan','Abeysekara','0777778888','680 Cedar Dr, Monaragala'),('C0018','Thilina','Gunawardana','0778889999','791 Birch Ave, Puttalam'),('C0019','Chatura','Herath','0779990000','802 Elm St, Kegalle'),('C0020','Hansika','Pathirana','0770001111','913 Ash Rd, Vavuniya'),('C0021','Ashan','Gamage','0771122334','024 Willow Ln, Mullaitivu'),('C0022','Dinusha','Seneviratne','0772233445','135 Poplar Dr, Mannar'),('C0023','Thusitha','Cooray','0773344556','246 Spruce St, Kilinochchi'),('C0024','Praveen','Rathnayake','0774455667','357 Fir Ave, Nuwara Eliya'),('C0025','Nadeeka','Liyanage','0775566778','468 Redwood Rd, Matale'),('C0026','Udara','De Silva','0776677889','579 Sequoia Ln, Gampaha'),('C0027','Anuradha','Kumarasinghe','0777788990','680 Cypress Dr, Chilaw'),('C0028','Sachini','Pieris','0778899001','791 Magnolia St, Embilipitiya'),('C0029','Asanka','Fonseka','0779900112','802 Dogwood Ave, Horana'),('C0030','Piyumal','Weerasinghe','0770011223','913 Cherry Rd, Panadura');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
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
