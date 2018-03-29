-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: droppod
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `podcasts`
--

DROP TABLE IF EXISTS `podcasts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `podcasts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `thumbnail` mediumblob,
  `url` varchar(255) NOT NULL,
  `rating` int(11) DEFAULT NULL,
  `uri` varchar(255) NOT NULL,
  `thumbnail_url` varchar(255) DEFAULT NULL,
  `uuid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uri_UNIQUE` (`uri`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `podcasts`
--

LOCK TABLES `podcasts` WRITE;
/*!40000 ALTER TABLE `podcasts` DISABLE KEYS */;
INSERT INTO `podcasts` VALUES (15,NULL,'https://rss.simplecast.com/podcasts/2218/rss',NULL,'https://rss.simplecast.com/podcasts/2218/rss','https://media.simplecast.com/podcast/image/2218/1475772484-artwork.jpg','644342cf-27a5-11e8-bdf7-f8a9634ca8b2'),(16,NULL,'http://rosebuddies.libsyn.com/rss',NULL,'http://rosebuddies.libsyn.com/rss','http://static.libsyn.com/p/assets/5/d/7/d/5d7d8667a0ac3d43/wonderful_cover_art_final.png','6f0c4b6d-27a5-11e8-bdf7-f8a9634ca8b2'),(17,NULL,'http://www.maximumfun.org/feeds/wbp.xml',NULL,'http://www.maximumfun.org/feeds/wbp.xml','http://static.libsyn.com/p/assets/5/f/d/1/5fd1a0b52beb14e1/wsyv1b1600x1600.png','7ea4ef48-27a5-11e8-bdf7-f8a9634ca8b2'),(18,NULL,'http://maximumfun.org/feeds/tt.xml',NULL,'http://maximumfun.org/feeds/tt.xml','http://static.libsyn.com/p/assets/0/c/f/0/0cf08d1d9d6077f0/TheTurnaround_3.3.2.jpg','89213373-27a5-11e8-bdf7-f8a9634ca8b2'),(19,NULL,'http://maximumfun.org/feeds/rg.xml',NULL,'http://maximumfun.org/feeds/rg.xml','http://static.libsyn.com/p/assets/8/1/a/c/81acd042c46d6028/ReadingGlasses-PodcastSquareLogo.png','aec693f9-27a5-11e8-bdf7-f8a9634ca8b2'),(20,NULL,'http://maximumfun.org/feeds/are.xml',NULL,'http://maximumfun.org/feeds/are.xml','http://pg-act-90.s3-us-west-2.amazonaws.com/90/artwork/show.94.audioart.original.5sshp.jpg','353bbe8b-27a6-11e8-bdf7-f8a9634ca8b2'),(21,NULL,'http://adventurezone.libsyn.com/rss',NULL,'http://adventurezone.libsyn.com/rss','http://static.libsyn.com/p/assets/c/3/7/e/c37e81258bbd2c79/ADVENTURE_ZONE_EXPERIMENT_ART.jpg','4597732b-27a6-11e8-bdf7-f8a9634ca8b2'),(22,NULL,'http://feeds.feedburner.com/todayinthepast',NULL,'http://feeds.feedburner.com/todayinthepast','http://static.libsyn.com/p/assets/a/9/3/a/a93a1d094735ef9c/judge-john-hodgman-square-mustache.jpg','534522c8-27a6-11e8-bdf7-f8a9634ca8b2'),(23,NULL,'http://deadpilotssociety.libsyn.com/rss',NULL,'http://deadpilotssociety.libsyn.com/rss','http://static.libsyn.com/p/assets/8/c/d/a/8cda224d7f7aa4f9/DPS-CoverArt-1400x1400_1.jpg','ac905941-27c6-11e8-bdf7-f8a9634ca8b2');
/*!40000 ALTER TABLE `podcasts` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp850 */ ;
/*!50003 SET character_set_results = cp850 */ ;
/*!50003 SET collation_connection  = cp850_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `droppod`.`podcasts_BEFORE_INSERT`
BEFORE INSERT ON `droppod`.`podcasts`
FOR EACH ROW
BEGIN
 SET new.uuid = uuid();
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-28 20:34:43
