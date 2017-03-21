-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 17, 2017 at 05:29 PM
-- Server version: 5.7.9
-- PHP Version: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `crawler`
--
DROP DATABASE `crawler`;
CREATE DATABASE IF NOT EXISTS `crawler` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `crawler`;

-- --------------------------------------------------------

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
CREATE TABLE IF NOT EXISTS `position` (
  `Position` int(11) NOT NULL,
  `Token_ID` int(11) NOT NULL,
  `Site_ID` int(11) NOT NULL,
  PRIMARY KEY (`Position`,`Site_ID`),
  KEY `Token_ID` (`Token_ID`),
  KEY `Site_ID` (`Site_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sites`
--

DROP TABLE IF EXISTS `sites`;
CREATE TABLE IF NOT EXISTS `sites` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `URL` varchar(1000) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `URL` (`URL`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
CREATE TABLE IF NOT EXISTS `token` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Token` varchar(1000) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Token` (`Token`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `unsites`
--

DROP TABLE IF EXISTS `unsites`;
CREATE TABLE IF NOT EXISTS `unsites` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `URL` varchar(1000) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `URL` (`URL`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
