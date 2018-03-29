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
-- Table structure for table `podcasts_translations`
--

DROP TABLE IF EXISTS `podcasts_translations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `podcasts_translations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `podcast_id` int(11) NOT NULL,
  `language_code` varchar(5) NOT NULL,
  `podcast_name` varchar(255) DEFAULT NULL,
  `podcast_description` mediumtext,
  PRIMARY KEY (`id`),
  KEY `fk_podcasts_translations_languages_language_code_idx` (`language_code`),
  KEY `fk_podcasts_translations_podcasts_podcast_id_idx` (`podcast_id`),
  CONSTRAINT `fk_podcasts_translations_languages_language_code` FOREIGN KEY (`language_code`) REFERENCES `languages` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_podcasts_translations_podcasts_podcast_id` FOREIGN KEY (`podcast_id`) REFERENCES `podcasts` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `podcasts_translations`
--

LOCK TABLES `podcasts_translations` WRITE;
/*!40000 ALTER TABLE `podcasts_translations` DISABLE KEYS */;
INSERT INTO `podcasts_translations` VALUES (20,15,'en','Emoji Wrap — The Emoji Podcast from Emojipedia','? The world\'s number one emoji podcast. News and commentary on emojis, stickers, and all things mobile. Hosted by Emojipedia founder Jeremy Burge, with special guests.'),(21,16,'en','Wonderful!','Welcome to Wonderful! It’s an enthusiast podcast by Griffin and Rachel McElroy in which they discuss Very Good Things, and the Things that Make Them Good. Got a Good Thing you’re excited about that you want them to talk about on the show? Shoot ‘em an email at wonderfulpodcast@gmail.com.'),(22,17,'en','Who Shot Ya?','A movie podcast that isn\'t just a bunch of straight white dudes. Comic Ricky Carmona is joined by critics Alonso Duralde (The Wrap) and April Wolfe (LA Weekly) for a fast, funny flight through film. Who Shot Ya? is news, reviews and in-depth insight, beamed directly into your ears every week.'),(23,18,'en','The Turnaround with Jesse Thorn','The Turnaround is a new series about our greatest living interviewers, hosted by Jesse Thorn and produced by Maximum Fun and The Columbia Journalism Review. Featuring conversations with prominent interviewers about their careers and their craft, the show is a perfect resource for a new generation of storytellers and journalists. You\'ll hear Jesse speak with Larry King, Terry Gross, Werner Herzog, Audie Cornish, and so many more!'),(24,19,'en','Reading Glasses','Do you love books? Want to learn how to make the most of your reading life? Join hosts Brea Grant and Mallory O’Meara every week as they discuss tips and tricks for reading better on Reading Glasses!\nReading Glasses is a podcast designed to help you get more out of your literary experiences. As professional creatives and mega-readers, Mallory and Brea are experts on integrating a love of reading into a busy lifestyle. Reading Glasses listeners will learn how to vanquish their To-Be-Read piles, get pointers on organizing their bookshelves and hear reviews on the newest reading gadgets. Brea and Mallory also offer savvy advice on uniquely bookish problems. How do you climb out of a reading slump? How do you support authors while still getting books on the cheap? Where do you hide the bodies of the people who won’t stop talking while you’re trying to read?\nMallory and Brea engage in a spirited weekly half hour discussion geared towards all kinds of book lovers -- nerds, avid bookworms, comic fans and science fiction geeks, literary fiction readers, book hoarders, library users, people who prefer the company of words on a page to a crowded party and casual readers who want to read more. '),(25,15,'fr','Emoji Wrap - Le Podcast Emoji d&#39;Emojipédia','? Le podcast emoji numéro un au monde. Nouvelles et commentaires sur les emojis, les autocollants et tout ce qui est mobile. Hébergé par Jeremy Burge, fondateur d&#39;Emojipedia, avec des invités spéciaux.'),(26,16,'fr','Formidable!','Bienvenue à Wonderful! C&#39;est un podcast passionné par Griffin et Rachel McElroy dans lequel ils discutent de très bonnes choses, et les choses qui les rendent bonnes. Vous avez une bonne chose, vous êtes excité à propos de ce que vous voulez qu&#39;ils parlent de la série? Shoot &#39;em un email à wonderfulpodcast@gmail.com.'),(27,17,'fr','Qui a tiré sur Ya?','Un podcast de film qui n&#39;est pas juste une bande de mecs blancs. Le comique Ricky Carmona est rejoint par les critiques Alonso Duralde (The Wrap) et April Wolfe (LA Weekly) pour un vol rapide et amusant à travers le film. Qui a tiré sur Ya? Ce sont des nouvelles, des critiques et des analyses approfondies, diffusées directement dans vos oreilles chaque semaine.'),(28,19,'fr','Lunettes pour lire','Aimez-vous les livres? Voulez-vous apprendre à tirer le meilleur parti de votre vie de lecture? Joignez-vous aux hôtes Brea Grant et Mallory O&#39;Meara chaque semaine pour discuter des trucs et astuces pour mieux lire sur les lunettes de lecture! Reading Glasses est un podcast conçu pour vous aider à tirer le meilleur parti de vos expériences littéraires. En tant que créatifs professionnels et méga-lecteurs, Mallory et Brea sont des experts dans l&#39;intégration de l&#39;amour de la lecture dans un mode de vie occupé. Les auditeurs de lunettes de lecture apprendront à vaincre leurs tas de choses à lire, à obtenir des conseils sur l&#39;organisation de leurs étagères et à entendre des commentaires sur les derniers gadgets de lecture. Brea et Mallory offrent également des conseils avisés sur des problèmes livresques uniques. Comment sortez-vous d&#39;une crise de lecture? Comment soutenez-vous les auteurs tout en obtenant des livres à bon marché? Où cachez-vous les corps des personnes qui n&#39;arrêteront pas de parler pendant que vous essayez de lire? Mallory et Brea s&#39;engagent dans une discussion hebdomadaire animée d&#39;une demi-heure destinée à toutes sortes d&#39;amoureux du livre - nerds, passionnés de livres, fans de BD et de science-fiction, lecteurs de livres littéraires, collectionneurs de livres, utilisateurs de bibliothèques, une page pour une fête bondée et des lecteurs occasionnels qui veulent en savoir plus.'),(29,20,'en','Adam Ruins Everything','First, on the Adam Ruins Everything TV show, Adam Conover broke down widespread misconceptions about everything we take for granted. Now, join Adam as he sits down with the experts and stars from the show to go into even more detail.'),(30,21,'en','The Adventure Zone','Justin, Travis and Griffin McElroy from My Brother, My Brother and Me have recruited their dad for a campaign of high adventure. Join The McElroys every other Thursday as they kill a nauseating number of gerblins in ... The Adventure Zone!'),(31,22,'en','Judge John Hodgman','John Hodgman\'s Today in the Past podcast is now The Judge John Hodgman Podcast.  Have your pressing issues decided by Famous Minor Television Personality John Hodgman, Certified Judge.  If you\'d like John Hodgman to solve your pressing issue, simply email it, along with your phone number, to hodgman@maximumfun.org.  THAT IS ALL.'),(32,23,'en','Dead Pilots Society','In Dead Pilots Society, scripts that were developed by studios and networks but were never produced are given the table reads they deserve. Starring actors you know and love from television and film, a live audience, and a good time in which no one gets notes, no one is fired, and everyone laughs. Presented by Andrew Reich (Friends; Worst Week) and Ben Blacker (The Writers Panel podcast; co-creator, Thrilling Adventure Hour).');
/*!40000 ALTER TABLE `podcasts_translations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-28 20:34:42
