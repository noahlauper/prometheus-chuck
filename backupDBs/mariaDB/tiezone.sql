
--
-- Current Database: `tiezone`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `tiezone` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `tiezone`;

--
-- Table structure for table `Floor`
--

DROP TABLE IF EXISTS `Floor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Floor` (
  `floorID` int(11) NOT NULL AUTO_INCREMENT,
  `höhe` int(10) NOT NULL,
  `breite` int(10) NOT NULL,
  PRIMARY KEY (`floorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Floor`
--

LOCK TABLES `Floor` WRITE;
/*!40000 ALTER TABLE `Floor` DISABLE KEYS */;
/*!40000 ALTER TABLE `Floor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Lager`
--

DROP TABLE IF EXISTS `Lager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Lager` (
  `lagerID` int(10) NOT NULL AUTO_INCREMENT,
  `regalIDFs` int(11) NOT NULL,
  `länge` int(100) NOT NULL,
  `breite` int(100) NOT NULL,
  `höhe` int(100) NOT NULL,
  PRIMARY KEY (`lagerID`),
  UNIQUE KEY `regalIDFs` (`regalIDFs`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Lager`
--

LOCK TABLES `Lager` WRITE;
/*!40000 ALTER TABLE `Lager` DISABLE KEYS */;
/*!40000 ALTER TABLE `Lager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Packet`
--

DROP TABLE IF EXISTS `Packet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Packet` (
  `packetID` int(11) NOT NULL AUTO_INCREMENT,
  `länge` int(11) NOT NULL,
  `breite` int(11) NOT NULL,
  `höhe` int(11) NOT NULL,
  `eintreffdatum` date NOT NULL,
  `auslieferungdatum` int(11) NOT NULL,
  PRIMARY KEY (`packetID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Packet`
--

LOCK TABLES `Packet` WRITE;
/*!40000 ALTER TABLE `Packet` DISABLE KEYS */;
/*!40000 ALTER TABLE `Packet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Regal`
--

DROP TABLE IF EXISTS `Regal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Regal` (
  `regalID` int(11) NOT NULL AUTO_INCREMENT,
  `packetIDFS` int(11) NOT NULL,
  `länge` int(100) NOT NULL,
  `breite` int(100) NOT NULL,
  `höhe` int(100) NOT NULL,
  `etagen` int(100) NOT NULL,
  PRIMARY KEY (`regalID`),
  UNIQUE KEY `packetIDFS` (`packetIDFS`),
  CONSTRAINT `Regal_ibfk_1` FOREIGN KEY (`regalID`) REFERENCES `Lager` (`regalIDFs`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Regal`
--

LOCK TABLES `Regal` WRITE;
/*!40000 ALTER TABLE `Regal` DISABLE KEYS */;
/*!40000 ALTER TABLE `Regal` ENABLE KEYS */;
UNLOCK TABLES;
