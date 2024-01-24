--
-- Current Database: `jsf`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `jsf` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `jsf`;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
                           `id` varchar(36) NOT NULL,
                           `created` datetime DEFAULT NULL,
                           `modified` datetime DEFAULT NULL,
                           `description` varchar(255) NOT NULL,
                           `working` bit(1) NOT NULL,
                           `name` varchar(60) NOT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_continents`
--

DROP TABLE IF EXISTS `company_continents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_continents` (
                                      `company_id` varchar(36) NOT NULL,
                                      `continents` varchar(255) DEFAULT NULL,
                                      KEY `FKdcmdslorkt37e5osnihblh8xs` (`company_id`),
                                      CONSTRAINT `FKdcmdslorkt37e5osnihblh8xs` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_continents`
--

LOCK TABLES `company_continents` WRITE;
/*!40000 ALTER TABLE `company_continents` DISABLE KEYS */;
/*!40000 ALTER TABLE `company_continents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endpoint`
--

DROP TABLE IF EXISTS `endpoint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endpoint` (
                            `id` varchar(36) NOT NULL,
                            `created` datetime DEFAULT NULL,
                            `modified` datetime DEFAULT NULL,
                            `method` int(11) NOT NULL,
                            `url` varchar(255) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endpoint`
--

LOCK TABLES `endpoint` WRITE;
/*!40000 ALTER TABLE `endpoint` DISABLE KEYS */;
/*!40000 ALTER TABLE `endpoint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `page`
--

DROP TABLE IF EXISTS `page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `page` (
                        `id` varchar(36) NOT NULL,
                        `created` datetime DEFAULT NULL,
                        `modified` datetime DEFAULT NULL,
                        `entity` varchar(255) DEFAULT NULL,
                        `icon` varchar(255) NOT NULL,
                        `available` bit(1) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `page`
--

LOCK TABLES `page` WRITE;
/*!40000 ALTER TABLE `page` DISABLE KEYS */;
/*!40000 ALTER TABLE `page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `page_endpoint`
--

DROP TABLE IF EXISTS `page_endpoint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `page_endpoint` (
                                 `page_id` varchar(36) NOT NULL,
                                 `endpoint_id` varchar(36) NOT NULL,
                                 KEY `FK9pqjfyjf235q98vad766q6swu` (`endpoint_id`),
                                 KEY `FK6b8efkk92sjbq7k8d4h3f21mq` (`page_id`),
                                 CONSTRAINT `FK6b8efkk92sjbq7k8d4h3f21mq` FOREIGN KEY (`page_id`) REFERENCES `page` (`id`),
                                 CONSTRAINT `FK9pqjfyjf235q98vad766q6swu` FOREIGN KEY (`endpoint_id`) REFERENCES `endpoint` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `page_endpoint`
--

LOCK TABLES `page_endpoint` WRITE;
/*!40000 ALTER TABLE `page_endpoint` DISABLE KEYS */;
/*!40000 ALTER TABLE `page_endpoint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
                           `id` varchar(36) NOT NULL,
                           `created` datetime DEFAULT NULL,
                           `modified` datetime DEFAULT NULL,
                           `amount` smallint(6) NOT NULL,
                           `availability` smallint(6) NOT NULL,
                           `currency` varchar(255) NOT NULL,
                           `description` varchar(255) NOT NULL,
                           `bio` char(1) NOT NULL,
                           `vegan` char(1) NOT NULL,
                           `name` varchar(60) NOT NULL,
                           `price` bigint(20) NOT NULL,
                           `company_id` varchar(36) DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FKghawd5rtv8ok565nwpdyyuto9` (`company_id`),
                           CONSTRAINT `FKghawd5rtv8ok565nwpdyyuto9` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_continents`
--

DROP TABLE IF EXISTS `product_continents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_continents` (
                                      `product_id` varchar(36) NOT NULL,
                                      `continents` varchar(255) DEFAULT NULL,
                                      KEY `FKn36s3s3xcx313iy4g8uc2mtw6` (`product_id`),
                                      CONSTRAINT `FKn36s3s3xcx313iy4g8uc2mtw6` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_continents`
--

LOCK TABLES `product_continents` WRITE;
/*!40000 ALTER TABLE `product_continents` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_continents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_ingredients`
--

DROP TABLE IF EXISTS `product_ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_ingredients` (
                                       `product_id` varchar(36) NOT NULL,
                                       `ingredients` varchar(255) DEFAULT NULL,
                                       KEY `FKcjxxp1v84pkvofdwi58x85431` (`product_id`),
                                       CONSTRAINT `FKcjxxp1v84pkvofdwi58x85431` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_ingredients`
--

LOCK TABLES `product_ingredients` WRITE;
/*!40000 ALTER TABLE `product_ingredients` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_ingredients` ENABLE KEYS */;
UNLOCK TABLES;