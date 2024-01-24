CREATE DATABASE /*!32312 IF NOT EXISTS*/ `Arbeitsjournal_tielau` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `Arbeitsjournal_tielau`;

--
-- Table structure for table `Tag`
--

DROP TABLE IF EXISTS `Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tag` (
                       `id` int(10) NOT NULL AUTO_INCREMENT,
                       `date` date NOT NULL DEFAULT current_timestamp(),
                       `task` char(255) DEFAULT NULL,
                       `description` varchar(255) DEFAULT NULL,
                       PRIMARY KEY (`id`) USING BTREE,
                       UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tag`
--

LOCK TABLES `Tag` WRITE;
/*!40000 ALTER TABLE `Tag` DISABLE KEYS */;
INSERT INTO `Tag` VALUES
                      (65,'2022-04-23','Doing: IE-4959 | Problems: Umgebung | Done: Docker mit nötigen Containers | Todo: IE-4959, Tests','sfdgfhdfghdfghdfgh'),
                      (67,'2022-04-23','Doing: IE-4959 | Problems: Umgebung | Done: Docker mit nötigen Containers | Todo: IE-4959, Tests','sfdgfhdfghdfghdfgh');
/*!40000 ALTER TABLE `Tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `Store_tielau`
--