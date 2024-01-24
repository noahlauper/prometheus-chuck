--
-- Current Database: `cba_db`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `cba_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `cba_db`;

--
-- Sequence structure for `hibernate_sequence`
--

DROP SEQUENCE IF EXISTS `hibernate_sequence`;
CREATE SEQUENCE `hibernate_sequence` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 1 cache 1000 nocycle ENGINE=InnoDB;
SELECT SETVAL(`hibernate_sequence`, 1001, 0);

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player` (
                          `playerid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                          `description` varchar(255) DEFAULT NULL,
                          `first_name` varchar(30) DEFAULT NULL,
                          `is_valid` bit(1) NOT NULL,
                          `last_name` varchar(30) DEFAULT NULL,
                          `number` int(11) NOT NULL,
                          `position` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`playerid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES
                         (6,NULL,'Kevin','','Vuiti',27,'Point Guard'),
                         (10,'','Lucas','','Millan',25,'Point Guard'),
                         (11,'','Kawhi','','Leonard',25,'Point Guard');
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                        `name` varchar(30) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES
                       (4,'USER'),
                       (5,'ADMIN'),
                       (7,'MODERATOR');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
                        `teamid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                        `team_city` varchar(255) DEFAULT NULL,
                        `team_coach` varchar(255) DEFAULT NULL,
                        `team_name` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`teamid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES
                       (8,'Brooklyn','KD','Nets'),
                       (9,'Chicago','Ben10','Bulls');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_team_players`
--

DROP TABLE IF EXISTS `team_team_players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_team_players` (
                                     `team_teamid` bigint(20) unsigned NOT NULL,
                                     `team_players_playerid` bigint(20) unsigned NOT NULL,
                                     UNIQUE KEY `UK_41e40y7b4vm3f2joqu4onvdtp` (`team_players_playerid`),
                                     KEY `FKl1pelkkgk9wxn8vvmouihxwwq` (`team_teamid`),
                                     CONSTRAINT `FK2f6agtvyl6eem94a82slsloy4` FOREIGN KEY (`team_players_playerid`) REFERENCES `player` (`playerid`),
                                     CONSTRAINT `FKl1pelkkgk9wxn8vvmouihxwwq` FOREIGN KEY (`team_teamid`) REFERENCES `team` (`teamid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_team_players`
--

LOCK TABLES `team_team_players` WRITE;
/*!40000 ALTER TABLE `team_team_players` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_team_players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                        `name` varchar(30) DEFAULT NULL,
                        `password` varchar(64) DEFAULT NULL,
                        `username` varchar(30) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
                       (1,'test','$2a$10$D0e6dhgfaG06Dnw0Kp2GDOEfLAZgOj4nVr93NHw97BSfZ/UriAU1y','test'),
                       (2,'test2','$2a$10$ejN33tIuq2TsSfJS88uJO.tfnIcGAYY1Z9XZmFNv8VdCTEpKZHMXS','test2'),
                       (3,'test3','$2a$10$n1w5qZD0FkanpNJiJrjWOeHLPADroK9zEnzzFb/luX3fe8kOt/LZu','test3');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
                              `user_id` bigint(20) unsigned NOT NULL,
                              `roles_id` bigint(20) unsigned NOT NULL,
                              KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
                              KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`),
                              CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                              CONSTRAINT `FKj9553ass9uctjrmh0gkqsmv0d` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES
                             (1,5),
                             (1,4),
                             (2,4);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

