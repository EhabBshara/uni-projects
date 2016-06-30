-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 30, 2016 at 04:58 AM
-- Server version: 5.7.9
-- PHP Version: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `plagiarism`
--

-- --------------------------------------------------------

--
-- Table structure for table `annotation`
--

DROP TABLE IF EXISTS `annotation`;
CREATE TABLE IF NOT EXISTS `annotation` (
  `annotation_id` int(11) NOT NULL AUTO_INCREMENT,
  `source_offset` bigint(20) DEFAULT NULL,
  `source_length` bigint(20) DEFAULT NULL,
  `suspicious_offset` bigint(20) DEFAULT NULL,
  `suspicious_length` bigint(20) DEFAULT NULL,
  `obfuscation` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `source_doc_id` int(11) DEFAULT NULL,
  `suspicious_doc_id` int(11) NOT NULL,
  PRIMARY KEY (`annotation_id`),
  KEY `fk_annotation_source_doc_idx` (`source_doc_id`),
  KEY `fk_annotation_suspicious_doc1_idx` (`suspicious_doc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `candidate`
--

DROP TABLE IF EXISTS `candidate`;
CREATE TABLE IF NOT EXISTS `candidate` (
  `candidate_id` int(11) NOT NULL AUTO_INCREMENT,
  `correlation_value` double DEFAULT NULL,
  `source_doc_id` int(11) NOT NULL,
  `suspicious_doc_id` int(11) NOT NULL,
  PRIMARY KEY (`candidate_id`),
  KEY `fk_source_doc1_idx` (`source_doc_id`),
  KEY `fk_suspicious_doc1_idx` (`suspicious_doc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `phrase`
--

DROP TABLE IF EXISTS `phrase`;
CREATE TABLE IF NOT EXISTS `phrase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pathname` varchar(200) DEFAULT NULL,
  `filename` varchar(200) DEFAULT NULL,
  `original` text,
  `tokens` text,
  `cleaned` text,
  `stemmed` text,
  `offset` int(11) DEFAULT NULL,
  `length` int(11) DEFAULT NULL,
  `source_doc_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_phrase_source_doc1_idx` (`source_doc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `source_doc`
--

DROP TABLE IF EXISTS `source_doc`;
CREATE TABLE IF NOT EXISTS `source_doc` (
  `source_doc_id` int(11) NOT NULL AUTO_INCREMENT,
  `source_doc_text` mediumtext,
  `source_doc_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`source_doc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `suspicious_doc`
--

DROP TABLE IF EXISTS `suspicious_doc`;
CREATE TABLE IF NOT EXISTS `suspicious_doc` (
  `suspicious_doc_id` int(11) NOT NULL AUTO_INCREMENT,
  `suspicious_doc_text` mediumtext,
  `suspicious_doc_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`suspicious_doc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `testphrase`
--

DROP TABLE IF EXISTS `testphrase`;
CREATE TABLE IF NOT EXISTS `testphrase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filename` varchar(200) DEFAULT NULL,
  `pathname` varchar(200) DEFAULT NULL,
  `phrase` text,
  `cleaned` text,
  `stemmed` text,
  `offset` int(11) DEFAULT NULL,
  `length` int(11) DEFAULT NULL,
  `suspicious_doc_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_testphrase_suspicious_doc1_idx` (`suspicious_doc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `annotation`
--
ALTER TABLE `annotation`
  ADD CONSTRAINT `fk_annotation_source_doc` FOREIGN KEY (`source_doc_id`) REFERENCES `source_doc` (`source_doc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_annotation_suspicious_doc1` FOREIGN KEY (`suspicious_doc_id`) REFERENCES `suspicious_doc` (`suspicious_doc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `assoc`
--
ALTER TABLE `assoc`
  ADD CONSTRAINT `fk_assoc_phrase1` FOREIGN KEY (`phrase_id`) REFERENCES `phrase` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_assoc_testphrase1` FOREIGN KEY (`testphrase_id`) REFERENCES `testphrase` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `candidate`
--
ALTER TABLE `candidate`
  ADD CONSTRAINT `fk_source_doc1` FOREIGN KEY (`source_doc_id`) REFERENCES `source_doc` (`source_doc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_suspicious_doc1` FOREIGN KEY (`suspicious_doc_id`) REFERENCES `suspicious_doc` (`suspicious_doc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `phrase`
--
ALTER TABLE `phrase`
  ADD CONSTRAINT `fk_phrase_source_doc1` FOREIGN KEY (`source_doc_id`) REFERENCES `source_doc` (`source_doc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `testphrase`
--
ALTER TABLE `testphrase`
  ADD CONSTRAINT `fk_testphrase_suspicious_doc1` FOREIGN KEY (`suspicious_doc_id`) REFERENCES `suspicious_doc` (`suspicious_doc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
