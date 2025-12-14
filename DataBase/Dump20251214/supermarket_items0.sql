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
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `Item_ID` varchar(25) NOT NULL,
  `IName` varchar(50) DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  `Catergory` varchar(20) DEFAULT NULL,
  `Price` int DEFAULT NULL,
  PRIMARY KEY (`Item_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES ('ITM001','White Bread',150,'Bakery',120),('ITM002','Fresh Milk 1L',200,'Dairy',280),('ITM003','Basmati Rice 5kg',100,'Grains',1500),('ITM004','Chicken Breast 1kg',80,'Meat',950),('ITM005','Cheddar Cheese 200g',120,'Dairy',650),('ITM006','Orange Juice 1L',180,'Beverages',420),('ITM007','Tomatoes 1kg',250,'Vegetables',180),('ITM008','Potatoes 1kg',300,'Vegetables',150),('ITM009','Green Apples 1kg',150,'Fruits',480),('ITM010','Cooking Oil 1L',140,'Condiments',550),('ITM011','White Sugar 1kg',200,'Grains',220),('ITM012','Butter 250g',100,'Dairy',720),('ITM013','Eggs 12pcs',180,'Dairy',540),('ITM014','Chocolate Bar 100g',250,'Snacks',280),('ITM015','Instant Noodles',300,'Snacks',80),('ITM016','Tea Bags 100pcs',150,'Beverages',420),('ITM017','Coffee Powder 200g',100,'Beverages',680),('ITM018','Salmon Fillet 500g',60,'Seafood',1850),('ITM019','Yogurt 500ml',170,'Dairy',320),('ITM020','Bananas 1kg',200,'Fruits',240),('ITM021','Carrots 1kg',180,'Vegetables',160),('ITM022','Onions 1kg',220,'Vegetables',140),('ITM023','Frozen Peas 500g',130,'Frozen',380),('ITM024','Ice Cream 1L',90,'Frozen',850),('ITM025','Pasta 500g',160,'Grains',320),('ITM026','Tomato Sauce 500ml',140,'Condiments',280),('ITM027','Dishwashing Liquid 1L',120,'Household',450),('ITM028','Toilet Paper 12rolls',200,'Household',620),('ITM029','Laundry Detergent 1kg',110,'Household',780),('ITM030','Mineral Water 1.5L',250,'Beverages',90);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
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
