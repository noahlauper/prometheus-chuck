CREATE DATABASE /*!32312 IF NOT EXISTS*/ `Store_tielau` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `Store_tielau`;

--
-- Table structure for table `attribute`
--

DROP TABLE IF EXISTS `attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attribute` (
                             `attributeID` int(11) NOT NULL AUTO_INCREMENT,
                             `type` text NOT NULL,
                             PRIMARY KEY (`attributeID`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
INSERT INTO `attribute` VALUES
                            (1,'cloth'),
                            (30,'jeans, jeans'),
                            (31,'2, 2'),
                            (32,'l, l'),
                            (34,'test, test'),
                            (35,'test2'),
                            (36,'null'),
                            (37,'test'),
                            (38,'null');
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
                           `productID` int(11) NOT NULL AUTO_INCREMENT,
                           `name` varchar(40) NOT NULL,
                           `price` int(11) NOT NULL,
                           PRIMARY KEY (`productID`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES
                          (23,'tshirt',10),
                          (42,'pants',100),
                          (43,'tie',100),
                          (44,'h',2),
                          (46,'test',1),
                          (47,'test2',1),
                          (48,'TestObject',0),
                          (49,'test',14),
                          (50,'TestObject',0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_attribute`
--

DROP TABLE IF EXISTS `product_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_attribute` (
                                     `productIDFS` varchar(11) NOT NULL,
                                     `attributesIDFS` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_attribute`
--

LOCK TABLES `product_attribute` WRITE;
/*!40000 ALTER TABLE `product_attribute` DISABLE KEYS */;
INSERT INTO `product_attribute` VALUES
                                    ('23','1'),
                                    ('42','30'),
                                    ('43','31'),
                                    ('44','32'),
                                    ('46','34'),
                                    ('47','35'),
                                    ('47','35'),
                                    ('47','35'),
                                    ('47','35'),
                                    ('47','35'),
                                    ('47','35'),
                                    ('47','35'),
                                    ('48','36'),
                                    ('49','37'),
                                    ('50','38');
/*!40000 ALTER TABLE `product_attribute` ENABLE KEYS */;
UNLOCK TABLES;