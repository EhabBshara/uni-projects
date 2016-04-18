--
-- Database: `plagiarism`
--
CREATE DATABASE IF NOT EXISTS `plagiarism` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `plagiarism`;

-- --------------------------------------------------------

--
-- Table structure for table `assoc`
--

DROP TABLE IF EXISTS `assoc`;
CREATE TABLE IF NOT EXISTS `assoc` (
  `assoc_id` int(11) NOT NULL AUTO_INCREMENT,
  `score` double DEFAULT NULL,
  `phrase_id` int(11) NOT NULL,
  `testphrase_id` int(11) NOT NULL,
  PRIMARY KEY (`assoc_id`),
  KEY `fk_assoc_phrase1_idx` (`phrase_id`),
  KEY `fk_assoc_testphrase1_idx` (`testphrase_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assoc`
--

INSERT INTO `assoc` (`assoc_id`, `score`, `phrase_id`, `testphrase_id`) VALUES
(1, 99, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `phrase`
--

DROP TABLE IF EXISTS `phrase`;
CREATE TABLE IF NOT EXISTS `phrase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pathname` varchar(200) DEFAULT NULL,
  `filename` varchar(200) DEFAULT NULL,
  `original` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `phrase`
--

INSERT INTO `phrase` (`id`, `pathname`, `filename`, `original`) VALUES
(1, 'Ali wassouf', 'Aliano Paco', 'I hat you '),
(4, NULL, NULL, NULL),
(13, NULL, NULL, NULL),
(14, NULL, NULL, NULL),
(15, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `testphrase`
--

DROP TABLE IF EXISTS `testphrase`;
CREATE TABLE IF NOT EXISTS `testphrase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filename` varchar(200) DEFAULT NULL,
  `pathname` varchar(200) DEFAULT NULL,
  `phrase` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `testphrase`
--

INSERT INTO `testphrase` (`id`, `filename`, `pathname`, `phrase`) VALUES
(1, 'Aliano', 'Georgina', 'I love you');

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
CREATE TABLE IF NOT EXISTS `token` (
  `token_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(45) DEFAULT NULL,
  `phrase_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`token_id`),
  KEY `fk_phrase_id` (`phrase_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `token`
--

INSERT INTO `token` (`token_id`, `content`, `phrase_id`) VALUES
(10, 'Ali', 14),
(11, 'sam', 14),
(12, 'Ehab', 14),
(13, 'Ali', 15),
(14, 'sam', 15),
(15, 'Ehab', 15);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `assoc`
--
ALTER TABLE `assoc`
  ADD CONSTRAINT `fk_assoc_phrase1` FOREIGN KEY (`phrase_id`) REFERENCES `phrase` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_assoc_testphrase1` FOREIGN KEY (`testphrase_id`) REFERENCES `testphrase` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `token`
--
ALTER TABLE `token`
  ADD CONSTRAINT `fk_phrase_id` FOREIGN KEY (`phrase_id`) REFERENCES `phrase` (`id`);
