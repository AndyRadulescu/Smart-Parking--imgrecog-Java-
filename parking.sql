-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.20-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for parking
CREATE DATABASE IF NOT EXISTS `parking` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `parking`;

-- Dumping structure for table parking.parking
CREATE TABLE IF NOT EXISTS `parking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `availability` int(10) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table parking.parking: ~6 rows (approximately)
/*!40000 ALTER TABLE `parking` DISABLE KEYS */;
INSERT INTO `parking` (`id`, `availability`, `name`) VALUES
	(1, 1, 'A'),
	(2, 1, 'A'),
	(3, 1, 'B'),
	(4, 0, 'B'),
	(5, 1, 'C'),
	(6, 1, 'C');
/*!40000 ALTER TABLE `parking` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
