
--
-- Current Database: `tietraining`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `tietraining` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `tietraining`;

--
-- Sequence structure for `execution_seq`
--

DROP SEQUENCE IF EXISTS `execution_seq`;
CREATE SEQUENCE `execution_seq` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 50 cache 1000 nocycle ENGINE=InnoDB;
SELECT SETVAL(`execution_seq`, 50001, 0);

--
-- Sequence structure for `plan_seq`
--

DROP SEQUENCE IF EXISTS `plan_seq`;
CREATE SEQUENCE `plan_seq` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 50 cache 1000 nocycle ENGINE=InnoDB;
SELECT SETVAL(`plan_seq`, 50001, 0);

--
-- Sequence structure for `workout_seq`
--

DROP SEQUENCE IF EXISTS `workout_seq`;
CREATE SEQUENCE `workout_seq` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 50 cache 1000 nocycle ENGINE=InnoDB;
SELECT SETVAL(`workout_seq`, 50001, 0);

--
-- Table structure for table `execution`
--

DROP TABLE IF EXISTS `execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `execution` (
                             `reps` int(11) NOT NULL,
                             `sets` int(11) NOT NULL,
                             `weight` int(11) NOT NULL,
                             `id` bigint(20) NOT NULL,
                             `exercise_name` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `execution`
--

LOCK TABLES `execution` WRITE;
/*!40000 ALTER TABLE `execution` DISABLE KEYS */;
INSERT INTO `execution` VALUES
                            (12,3,100,1,'Leg Press'),
                            (15,2,60,2,'Seated Hamstring'),
                            (5,2,80,3,'Barbell Full Squat'),
                            (8,4,21,4,'Rickshaw Carry'),
                            (12,5,100,5,'T-Bar Row with Handle'),
                            (10,2,70,6,'Weighted pull-up'),
                            (20,5,70,7,'Jog In Place'),
                            (2,2,3,8,'Slow Jog');
/*!40000 ALTER TABLE `execution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan` (
                        `id` bigint(20) NOT NULL,
                        `plan_name` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
INSERT INTO `plan` VALUES
                       (1,'standart plan'),
                       (2,'test'),
                       (3,'jkdjf');
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_workout_ids`
--

DROP TABLE IF EXISTS `plan_workout_ids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_workout_ids` (
                                    `plan_id` bigint(20) NOT NULL,
                                    `workout_ids` bigint(20) DEFAULT NULL,
                                    KEY `FK4gtcc1bqswfko19klvi3ui2rf` (`plan_id`),
                                    CONSTRAINT `FK4gtcc1bqswfko19klvi3ui2rf` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_workout_ids`
--

LOCK TABLES `plan_workout_ids` WRITE;
/*!40000 ALTER TABLE `plan_workout_ids` DISABLE KEYS */;
INSERT INTO `plan_workout_ids` VALUES
                                   (2,1),
                                   (2,2),
                                   (3,3),
                                   (1,1),
                                   (1,4);
/*!40000 ALTER TABLE `plan_workout_ids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout`
--

DROP TABLE IF EXISTS `workout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workout` (
                           `id` bigint(20) NOT NULL,
                           `duration` varchar(255) DEFAULT NULL,
                           `workout_name` varchar(255) DEFAULT NULL,
                           `weekdays` varbinary(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout`
--

LOCK TABLES `workout` WRITE;
/*!40000 ALTER TABLE `workout` DISABLE KEYS */;
INSERT INTO `workout` VALUES
                          (1,'90','legday','��\0ur\0[Ljava.lang.Byte;�l/�h�n\0\0xp\0\0\0sr\0java.lang.Byte�N`��P�\0B\0valuexr\0java.lang.Number������\0\0xpsq\0~\0'),
                          (2,'100','push day','��\0ur\0[Ljava.lang.Byte;�l/�h�n\0\0xp\0\0\0sr\0java.lang.Byte�N`��P�\0B\0valuexr\0java.lang.Number������\0\0xpsq\0~\0'),
                          (3,'120','pull day','��\0ur\0[Ljava.lang.Byte;�l/�h�n\0\0xp\0\0\0sr\0java.lang.Byte�N`��P�\0B\0valuexr\0java.lang.Number������\0\0xp\0sq\0~\0'),
                          (4,'50','cardio','��\0ur\0[Ljava.lang.Byte;�l/�h�n\0\0xp\0\0\0sr\0java.lang.Byte�N`��P�\0B\0valuexr\0java.lang.Number������\0\0xp');
/*!40000 ALTER TABLE `workout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_execution_ids`
--

DROP TABLE IF EXISTS `workout_execution_ids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workout_execution_ids` (
                                         `execution_ids` bigint(20) DEFAULT NULL,
                                         `workout_id` bigint(20) NOT NULL,
                                         KEY `FKsw9s35ddlafn6p2a04udfechh` (`workout_id`),
                                         CONSTRAINT `FKsw9s35ddlafn6p2a04udfechh` FOREIGN KEY (`workout_id`) REFERENCES `workout` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_execution_ids`
--

LOCK TABLES `workout_execution_ids` WRITE;
/*!40000 ALTER TABLE `workout_execution_ids` DISABLE KEYS */;
INSERT INTO `workout_execution_ids` VALUES
                                        (1,1),
                                        (2,1),
                                        (3,1),
                                        (4,2),
                                        (5,3),
                                        (6,3),
                                        (7,4),
                                        (8,4);
/*!40000 ALTER TABLE `workout_execution_ids` ENABLE KEYS */;
UNLOCK TABLES;
