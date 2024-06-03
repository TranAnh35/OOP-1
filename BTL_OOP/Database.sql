-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: library_oop1
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `muonsachbook`
--

DROP TABLE IF EXISTS `muonsachbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `muonsachbook` (
  `ID` int NOT NULL,
  `TenSach` varchar(255) DEFAULT NULL,
  `TenNguoiMuon` varchar(255) DEFAULT NULL,
  `NgayMuon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `muonsachbook`
--

LOCK TABLES `muonsachbook` WRITE;
/*!40000 ALTER TABLE `muonsachbook` DISABLE KEYS */;
INSERT INTO `muonsachbook` VALUES (1,'Lê Anh liệu có đẹp trai','Duyên','0967803949');
/*!40000 ALTER TABLE `muonsachbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nguoimuonbook`
--

DROP TABLE IF EXISTS `nguoimuonbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nguoimuonbook` (
  `ID` int NOT NULL,
  `TenNguoiMuon` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `soDienThoai` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguoimuonbook`
--

LOCK TABLES `nguoimuonbook` WRITE;
/*!40000 ALTER TABLE `nguoimuonbook` DISABLE KEYS */;
INSERT INTO `nguoimuonbook` VALUES (1,'Duyên','test@gmail.com','0967803939'),(2,'Hồng Duyên','test@gmail.com','0967803949');
/*!40000 ALTER TABLE `nguoimuonbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thuvienbook`
--

DROP TABLE IF EXISTS `thuvienbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thuvienbook` (
  `ID` int NOT NULL,
  `TieuDeSach` varchar(255) DEFAULT NULL,
  `TacGia` varchar(255) DEFAULT NULL,
  `NamXuatBan` int DEFAULT NULL,
  `SoLuong` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thuvienbook`
--

LOCK TABLES `thuvienbook` WRITE;
/*!40000 ALTER TABLE `thuvienbook` DISABLE KEYS */;
INSERT INTO `thuvienbook` VALUES (1,'Lê Anh liệu có đẹp trai','Lương',2024,10),(2,'Duyên liệu có tròn xoe','Lương',2024,5);
/*!40000 ALTER TABLE `thuvienbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thuvienebook`
--

DROP TABLE IF EXISTS `thuvienebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thuvienebook` (
  `ID` int NOT NULL,
  `TenSach` varchar(255) DEFAULT NULL,
  `TacGia` varchar(255) DEFAULT NULL,
  `NamXuatBan` int DEFAULT NULL,
  `SoLuong` int DEFAULT NULL,
  `DinhDangFile` varchar(255) DEFAULT NULL,
  `KichThuocFile` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thuvienebook`
--

LOCK TABLES `thuvienebook` WRITE;
/*!40000 ALTER TABLE `thuvienebook` DISABLE KEYS */;
INSERT INTO `thuvienebook` VALUES (3,'Học khó quá thì nên làm gì','Không có tên riêng đâu',2024,1,'pdf',2);
/*!40000 ALTER TABLE `thuvienebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trasachbook`
--

DROP TABLE IF EXISTS `trasachbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trasachbook` (
  `ID` int NOT NULL,
  `TenNguoiMuon` varchar(255) DEFAULT NULL,
  `NgayTra` date DEFAULT NULL,
  `KhoanPhat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trasachbook`
--

LOCK TABLES `trasachbook` WRITE;
/*!40000 ALTER TABLE `trasachbook` DISABLE KEYS */;
/*!40000 ALTER TABLE `trasachbook` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-31  3:16:08
