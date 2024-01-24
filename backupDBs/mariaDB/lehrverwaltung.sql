--
-- Current Database: `lehrverwaltung`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `lehrverwaltung` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `lehrverwaltung`;

--
-- Table structure for table `exercise`
--

DROP TABLE IF EXISTS `exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exercise` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `requirement` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise`
--

LOCK TABLES `exercise` WRITE;
/*!40000 ALTER TABLE `exercise` DISABLE KEYS */;
INSERT INTO `exercise` VALUES
(1,'sasdasaddsa','sasdasassda','Java'),
(2,'saasdsdaasd','asasasdasdds','Java'),
(3,'asssssssssss','sasdaasddasasddsasdadsaa','Java');
/*!40000 ALTER TABLE `exercise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_description`
--

DROP TABLE IF EXISTS `exercise_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exercise_description` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text DEFAULT NULL,
  `exercise_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5m9ocihet0aoesnekcj8bquql` (`exercise_fk`),
  CONSTRAINT `FK5m9ocihet0aoesnekcj8bquql` FOREIGN KEY (`exercise_fk`) REFERENCES `exercise` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_description`
--

LOCK TABLES `exercise_description` WRITE;
/*!40000 ALTER TABLE `exercise_description` DISABLE KEYS */;
INSERT INTO `exercise_description` VALUES
(3,'saddsasdsdsdsasasdadsadsa',3);
/*!40000 ALTER TABLE `exercise_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_link`
--

DROP TABLE IF EXISTS `exercise_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exercise_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `link` varchar(255) DEFAULT NULL,
  `exercise_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKen4ftdqdbg9um117jg3ikv1mm` (`exercise_fk`),
  CONSTRAINT `FKen4ftdqdbg9um117jg3ikv1mm` FOREIGN KEY (`exercise_fk`) REFERENCES `exercise` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_link`
--

LOCK TABLES `exercise_link` WRITE;
/*!40000 ALTER TABLE `exercise_link` DISABLE KEYS */;
INSERT INTO `exercise_link` VALUES
(1,'https://test.ch',1),
(2,'https://test.ch',2),
(3,'https://test.ch',3);
/*!40000 ALTER TABLE `exercise_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_privateio`
--

DROP TABLE IF EXISTS `exercise_privateio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exercise_privateio` (
  `exercise_id` int(11) NOT NULL,
  `privateio` varchar(255) DEFAULT NULL,
  KEY `FK4ycs56fvm62nwxk70wccnqi6f` (`exercise_id`),
  CONSTRAINT `FK4ycs56fvm62nwxk70wccnqi6f` FOREIGN KEY (`exercise_id`) REFERENCES `exercise` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_privateio`
--

LOCK TABLES `exercise_privateio` WRITE;
/*!40000 ALTER TABLE `exercise_privateio` DISABLE KEYS */;
INSERT INTO `exercise_privateio` VALUES
(1,'asdsda'),
(1,'asdsda'),
(2,'sasadasd'),
(2,'sasadasd');
/*!40000 ALTER TABLE `exercise_privateio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_publicio`
--

DROP TABLE IF EXISTS `exercise_publicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exercise_publicio` (
  `exercise_id` int(11) NOT NULL,
  `publicio` varchar(255) DEFAULT NULL,
  KEY `FK8lgxift75qfut9h8tlh1r95g5` (`exercise_id`),
  CONSTRAINT `FK8lgxift75qfut9h8tlh1r95g5` FOREIGN KEY (`exercise_id`) REFERENCES `exercise` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_publicio`
--

LOCK TABLES `exercise_publicio` WRITE;
/*!40000 ALTER TABLE `exercise_publicio` DISABLE KEYS */;
INSERT INTO `exercise_publicio` VALUES
(1,'dsasdasda'),
(1,'asdsdassadd'),
(2,'sadsdaasdsda'),
(2,'sadsaasdsadsadsda');
/*!40000 ALTER TABLE `exercise_publicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_object`
--

DROP TABLE IF EXISTS `file_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_object` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` blob DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `exercise_fk` int(11) DEFAULT NULL,
  `run_fk` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgw7v1w4wixp9c2d0gsfnjnv57` (`exercise_fk`),
  KEY `FKsf3eq3f8fkdoq661bknlsfqk9` (`run_fk`),
  CONSTRAINT `FKgw7v1w4wixp9c2d0gsfnjnv57` FOREIGN KEY (`exercise_fk`) REFERENCES `exercise` (`id`),
  CONSTRAINT `FKsf3eq3f8fkdoq661bknlsfqk9` FOREIGN KEY (`run_fk`) REFERENCES `run` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_object`
--

LOCK TABLES `file_object` WRITE;
/*!40000 ALTER TABLE `file_object` DISABLE KEYS */;
INSERT INTO `file_object` VALUES
(1,'public class Fibonacci {\r\npublic static void main(String[] args) {\r\n//initializing the constants\r\nint n = 100, t1 = 0, t2 = 1;\r\nSystem.out.print(\"Upto \" + n + \": \");\r\n//while loop to calculate fibonacci series upto n numbers\r\nwhile (t1<= n)\r\n{\r\nSystem.out.print(t1 + \" + \");\r\nint sum = t1 + t2;\r\nt1 = t2;\r\nt2 = sum;\r\n}\r\n}\r\n}','fibonacci.java','',2,NULL);
/*!40000 ALTER TABLE `file_object` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `status` smallint(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `run_fk` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfs33mgdk1xp92mksayx46j35x` (`run_fk`),
  CONSTRAINT `FKfs33mgdk1xp92mksayx46j35x` FOREIGN KEY (`run_fk`) REFERENCES `run` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `rolename` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `rolename` (`rolename`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES
(1,'Lehrling'),
(2,'Lehrmeister');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `run`
--

DROP TABLE IF EXISTS `run`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `run` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `abbreviation` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `runtime` int(11) NOT NULL,
  `run_fk` int(11) DEFAULT NULL,
  `exercise_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3xji4jsh3ul829gthgxvacnsa` (`run_fk`),
  KEY `FKa8pqsr7opkr2okex96gmqbaxu` (`exercise_fk`),
  CONSTRAINT `FK3xji4jsh3ul829gthgxvacnsa` FOREIGN KEY (`run_fk`) REFERENCES `notification` (`id`),
  CONSTRAINT `FKa8pqsr7opkr2okex96gmqbaxu` FOREIGN KEY (`exercise_fk`) REFERENCES `exercise` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `run`
--

LOCK TABLES `run` WRITE;
/*!40000 ALTER TABLE `run` DISABLE KEYS */;
/*!40000 ALTER TABLE `run` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(8) NOT NULL,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_fk` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `role_fk` (`role_fk`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_fk`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
(10,'tiekom','Andrey','Di Claudio','$2a$10$ut5gGWiMdjjdByRkwJdJhOVep84jpqRUvAybcfzu72L8BQI/IXRZ2',2),
(11,'tiehof','David','Hofmann','$2y$12$AYFGkFU5OFyGtRrFDavqIuT0/csK8Ovx7L..UH.v7WMSO/X3FVf2q',2),
(13,'tieael','Lucas','Milan','$2a$10$MASI1.E8uA2L.t/aFka1JuWjKqcDmOjOYPYf8BH9AKeQCTxtEYhbq',1),
(14,'tiemill','Lucas','Millan','$2a$10$9ULF10eUi3Amm0Ru4ffStObzCkR/oqUH65Qrk8j11mSpUpZD79Uta',1),
(16,'tielau','Noah','Lauper','$2a$10$.N/R1YBO9x6kbcN3cWLpE.VUqefiqHCvrk/MFx8jcObDlpSh3kN1K',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;