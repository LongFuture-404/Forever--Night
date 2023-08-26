-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: forever__night
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `first_navigation`
--

DROP TABLE IF EXISTS `first_navigation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `first_navigation` (
  `first_navigation_id` varchar(5) NOT NULL,
  `first_navigation_name` varchar(45) DEFAULT NULL,
  `first_navigation_key` varchar(45) DEFAULT NULL,
  `first_navigation_able` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`first_navigation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `first_navigation`
--

LOCK TABLES `first_navigation` WRITE;
/*!40000 ALTER TABLE `first_navigation` DISABLE KEYS */;
INSERT INTO `first_navigation` VALUES ('1','推荐','1','1'),('2','建站','1','1'),('3','社区','1','1'),('4','文档','1','1'),('5','APP','1','1');
/*!40000 ALTER TABLE `first_navigation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `permission_id` varchar(5) NOT NULL,
  `permission_name` varchar(10) DEFAULT NULL,
  `permission_url` varchar(50) DEFAULT NULL,
  `permission_key` varchar(5) DEFAULT NULL,
  `permission_type` int DEFAULT NULL,
  `parent_id` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES ('1','用户管理','/userMangage','1',1,'0'),('11','用户查询','/register','1',2,'1'),('12','用户添加','/UserInsert','1',2,'1'),('13','用户删除','/UserDelete','1',2,'1'),('14','用户详情','/UserMore','1',2,'1'),('2','图片管理','/PictrueManage','1',1,'0'),('21','图片添加','/PictrueInsert','1',2,'2'),('22','图片删除','/PictrueDelete','1',2,'2'),('3','视频管理','/VideoManage','1',1,'0'),('31','视频添加','/VideoInsert','1',2,'3'),('32','视频删除','/VideoDelete','1',2,'3');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` varchar(5) NOT NULL,
  `role_code` varchar(20) DEFAULT NULL,
  `role_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('1','admin','超级管理员');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `role_count` varchar(45) NOT NULL,
  `role_id` varchar(5) DEFAULT NULL,
  `permission_id` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`role_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES ('1','1','1'),('10','1','31'),('11','1','32'),('2','1','11'),('3','1','12'),('4','1','13'),('5','1','14'),('6','1','2'),('7','1','21'),('8','1','22'),('9','1','3');
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `second_navigation`
--

DROP TABLE IF EXISTS `second_navigation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `second_navigation` (
  `second_navigation_id` varchar(5) NOT NULL,
  `second_navigation_name` varchar(45) DEFAULT NULL,
  `second_navigation_url` varchar(45) DEFAULT NULL,
  `second_navigation_able` varchar(45) DEFAULT NULL,
  `parent_id` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`second_navigation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `second_navigation`
--

LOCK TABLES `second_navigation` WRITE;
/*!40000 ALTER TABLE `second_navigation` DISABLE KEYS */;
INSERT INTO `second_navigation` VALUES ('11','最新资讯','/newNews','1','1'),('12','版本热点','/newHot','1','1'),('21','人员变动','/userChange','1','2'),('22','资金','/money','1','2'),('31','社区规则','/socialRole','1','3'),('41','角色','/chartactor','1','4'),('51','贡献','/devote','1','5');
/*!40000 ALTER TABLE `second_navigation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` varchar(20) NOT NULL,
  `userName` varchar(25) DEFAULT NULL,
  `password` varchar(25) DEFAULT NULL,
  `image_url` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('18011411638','zcl','123456',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `userId` varchar(20) NOT NULL,
  `role_id` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES ('18011411638','1');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-25 16:07:42
