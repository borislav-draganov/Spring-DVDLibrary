USE `dvdlibrary`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: dvdlibrary
-- ------------------------------------------------------
-- Server version	5.6.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `audio`
--
-- ORDER BY:  `idAudio`

/*!40000 ALTER TABLE `audio` DISABLE KEYS */;
INSERT INTO `audio` (`idAudio`, `audio`) VALUES (1,'Bulgarian'),(2,'English'),(3,'Japanese');
/*!40000 ALTER TABLE `audio` ENABLE KEYS */;

--
-- Dumping data for table `dvd`
--
-- ORDER BY:  `idDVD`

/*!40000 ALTER TABLE `dvd` DISABLE KEYS */;
INSERT INTO `dvd` (`idDVD`, `movieId`, `ISBN`, `edition`, `screenFormat`, `region`) VALUES (1,1,'0-8044-2957-X','Standart','16:9','JP'),(2,2,'85-359-0277-5','Standart','16:9','BG'),(3,3,'99921-58-10-7','Gold','16:9','EN');
/*!40000 ALTER TABLE `dvd` ENABLE KEYS */;

--
-- Dumping data for table `dvd_has_language_audio`
--
-- ORDER BY:  `DVD_idDVD`,`Language_idLanguage`,`Audio_idAudio`

/*!40000 ALTER TABLE `dvd_has_language_audio` DISABLE KEYS */;
INSERT INTO `dvd_has_language_audio` (`DVD_idDVD`, `Language_idLanguage`, `Audio_idAudio`) VALUES (1,2,2),(1,2,3),(2,1,1),(3,2,2),(3,3,2);
/*!40000 ALTER TABLE `dvd_has_language_audio` ENABLE KEYS */;

--
-- Dumping data for table `genre`
--
-- ORDER BY:  `idGenre`

/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` (`idGenre`, `Genre`) VALUES (1,'Sci-Fi'),(2,'Comedy'),(3,'Adventure');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;

--
-- Dumping data for table `language`
--
-- ORDER BY:  `idLanguage`

/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` (`idLanguage`, `language`) VALUES (1,'Bulgarian'),(2,'English'),(3,'German');
/*!40000 ALTER TABLE `language` ENABLE KEYS */;

--
-- Dumping data for table `movie`
--
-- ORDER BY:  `idMovie`

/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` (`idMovie`, `title`, `length`, `releaseDate`, `IMDB`, `rating`) VALUES (1,'The Avengers',143,'2014-05-04',848228,2),(2,'X-Men: The Last Stand',104,'2006-05-26',376994,1),(3,'Liar Liar',86,'1997-03-21',119528,3);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;

--
-- Dumping data for table `movie_has_genre`
--
-- ORDER BY:  `Movie_idMovie`,`Genre_idGenre`

/*!40000 ALTER TABLE `movie_has_genre` DISABLE KEYS */;
INSERT INTO `movie_has_genre` (`Movie_idMovie`, `Genre_idGenre`) VALUES (1,1),(1,3),(2,1),(2,3),(3,2);
/*!40000 ALTER TABLE `movie_has_genre` ENABLE KEYS */;

--
-- Dumping data for table `rating`
--
-- ORDER BY:  `idRating`

/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` (`idRating`, `rating`) VALUES (1,'Horrible'),(2,'Mediocre'),(3,'Awesome');
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-17 12:01:26
