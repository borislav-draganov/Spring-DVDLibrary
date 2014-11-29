SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema DVDLibrary
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `DVDLibrary` ;
CREATE SCHEMA IF NOT EXISTS `DVDLibrary` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `DVDLibrary` ;

-- -----------------------------------------------------
-- Table `DVDLibrary`.`Rating`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DVDLibrary`.`Rating` ;

CREATE TABLE IF NOT EXISTS `DVDLibrary`.`Rating` (
  `idRating` INT NOT NULL AUTO_INCREMENT,
  `rating` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idRating`),
  UNIQUE INDEX `rating_UNIQUE` (`rating` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DVDLibrary`.`Movie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DVDLibrary`.`Movie` ;

CREATE TABLE IF NOT EXISTS `DVDLibrary`.`Movie` (
  `idMovie` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `length` INT NULL,
  `releaseDate` DATE NULL,
  `IMDB` INT NULL,
  `rating` INT NOT NULL,
  PRIMARY KEY (`idMovie`),
  INDEX `Rating_idx` (`rating` ASC),
  CONSTRAINT `Rating`
    FOREIGN KEY (`rating`)
    REFERENCES `DVDLibrary`.`Rating` (`idRating`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DVDLibrary`.`Genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DVDLibrary`.`Genre` ;

CREATE TABLE IF NOT EXISTS `DVDLibrary`.`Genre` (
  `idGenre` INT NOT NULL AUTO_INCREMENT,
  `Genre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idGenre`),
  UNIQUE INDEX `Genre_UNIQUE` (`Genre` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DVDLibrary`.`DVD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DVDLibrary`.`DVD` ;

CREATE TABLE IF NOT EXISTS `DVDLibrary`.`DVD` (
  `idDVD` INT NOT NULL AUTO_INCREMENT,
  `movieId` INT NOT NULL,
  `ISBN` VARCHAR(45) NOT NULL,
  `edition` VARCHAR(45) NULL,
  `screenFormat` VARCHAR(45) NULL,
  `region` VARCHAR(45) NULL,
  PRIMARY KEY (`idDVD`),
  INDEX `Movie_idx` (`movieId` ASC),
  UNIQUE INDEX `ISBN_UNIQUE` (`ISBN` ASC),
  CONSTRAINT `DVD_Movie`
    FOREIGN KEY (`movieId`)
    REFERENCES `DVDLibrary`.`Movie` (`idMovie`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DVDLibrary`.`Language`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DVDLibrary`.`Language` ;

CREATE TABLE IF NOT EXISTS `DVDLibrary`.`Language` (
  `idLanguage` INT NOT NULL AUTO_INCREMENT,
  `language` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idLanguage`),
  UNIQUE INDEX `language_UNIQUE` (`language` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DVDLibrary`.`Audio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DVDLibrary`.`Audio` ;

CREATE TABLE IF NOT EXISTS `DVDLibrary`.`Audio` (
  `idAudio` INT NOT NULL AUTO_INCREMENT,
  `audio` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idAudio`),
  UNIQUE INDEX `audio_UNIQUE` (`audio` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DVDLibrary`.`Movie_has_Genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DVDLibrary`.`Movie_has_Genre` ;

CREATE TABLE IF NOT EXISTS `DVDLibrary`.`Movie_has_Genre` (
  `Movie_idMovie` INT NOT NULL,
  `Genre_idGenre` INT NOT NULL,
  PRIMARY KEY (`Movie_idMovie`, `Genre_idGenre`),
  INDEX `fk_Movie_has_Genre_Genre1_idx` (`Genre_idGenre` ASC),
  INDEX `fk_Movie_has_Genre_Movie_idx` (`Movie_idMovie` ASC),
  CONSTRAINT `Movie`
    FOREIGN KEY (`Movie_idMovie`)
    REFERENCES `DVDLibrary`.`Movie` (`idMovie`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Genre`
    FOREIGN KEY (`Genre_idGenre`)
    REFERENCES `DVDLibrary`.`Genre` (`idGenre`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DVDLibrary`.`DVD_has_Language_Audio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DVDLibrary`.`DVD_has_Language_Audio` ;

CREATE TABLE IF NOT EXISTS `DVDLibrary`.`DVD_has_Language_Audio` (
  `DVD_idDVD` INT NOT NULL,
  `Language_idLanguage` INT NOT NULL,
  `Audio_idAudio` INT NOT NULL,
  PRIMARY KEY (`DVD_idDVD`, `Language_idLanguage`, `Audio_idAudio`),
  INDEX `fk_DVD_has_Language_Language1_idx` (`Language_idLanguage` ASC),
  INDEX `fk_DVD_has_Language_DVD1_idx` (`DVD_idDVD` ASC),
  INDEX `Audio_idx` (`Audio_idAudio` ASC),
  CONSTRAINT `DVD`
    FOREIGN KEY (`DVD_idDVD`)
    REFERENCES `DVDLibrary`.`DVD` (`idDVD`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Language`
    FOREIGN KEY (`Language_idLanguage`)
    REFERENCES `DVDLibrary`.`Language` (`idLanguage`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Audio`
    FOREIGN KEY (`Audio_idAudio`)
    REFERENCES `DVDLibrary`.`Audio` (`idAudio`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `DVDLibrary` ;

-- -----------------------------------------------------
-- Placeholder table for view `DVDLibrary`.`view_all_movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DVDLibrary`.`view_all_movies` (`idMovie` INT, `title` INT, `length` INT, `releaseDate` INT, `IMDB` INT, `rating` INT, `GenreIDs` INT, `Genres` INT);

-- -----------------------------------------------------
-- Placeholder table for view `DVDLibrary`.`view_all_audios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DVDLibrary`.`view_all_audios` (`id` INT);

-- -----------------------------------------------------
-- Placeholder table for view `DVDLibrary`.`view_all_languages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DVDLibrary`.`view_all_languages` (`id` INT);

-- -----------------------------------------------------
-- Placeholder table for view `DVDLibrary`.`view_all_genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DVDLibrary`.`view_all_genres` (`id` INT);

-- -----------------------------------------------------
-- Placeholder table for view `DVDLibrary`.`view_all_dvds`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DVDLibrary`.`view_all_dvds` (`idDVD` INT, `idMovie` INT, `title` INT, `length` INT, `releaseDate` INT, `IMDB` INT, `rating` INT, `GenreIds` INT, `Genres` INT, `ISBN` INT, `edition` INT, `screenFormat` INT, `region` INT, `LanguageIDs` INT, `Languages` INT, `AudioIDs` INT, `Audios` INT);

-- -----------------------------------------------------
-- Placeholder table for view `DVDLibrary`.`view_all_ratings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DVDLibrary`.`view_all_ratings` (`id` INT);

-- -----------------------------------------------------
-- procedure usp_add_audio
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_add_audio`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_add_audio`(IN inAudio VARCHAR(45))
BEGIN
	INSERT INTO Audio(audio) VALUES(inAudio);

	# Return Status Code and The ID of the new entry
	SELECT 0, LAST_INSERT_ID();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_add_genre
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_add_genre`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_add_genre`(IN inGenre VARCHAR(45))
BEGIN
	INSERT INTO Genre(genre) VALUES(inGenre);

	# Return Status Code and The ID of the new entry
	SELECT 0, LAST_INSERT_ID();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_add_language
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_add_language`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_add_language`(IN inLanguage VARCHAR(45))
BEGIN
	INSERT INTO Language(language) VALUES(inLanguage);

	# Return Status Code and The ID of the new entry
	SELECT 0, LAST_INSERT_ID();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_add_rating
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_add_rating`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_add_rating`(IN inRating VARCHAR(45))
BEGIN
	INSERT INTO Rating(rating) VALUES(inRating);

	# Return Status Code and The ID of the new entry
	SELECT 0, LAST_INSERT_ID();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_delete_audio_by_id
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_delete_audio_by_id`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_delete_audio_by_id`(IN inID INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idAudio FROM dvdlibrary.Audio WHERE idAudio = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Audio ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Delete record
	DELETE FROM DVDLibrary.Audio WHERE idAudio = inID;

	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_delete_dvd_by_id
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_delete_dvd_by_id`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_delete_dvd_by_id`(IN inID INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idDVD FROM dvdlibrary.DVD WHERE idDVD = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The DVD ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Delete record
	DELETE FROM DVDLibrary.DVD WHERE idDVD = inID;

	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_delete_genre_by_id
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_delete_genre_by_id`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_delete_genre_by_id`(IN inID INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idGenre FROM dvdlibrary.Genre WHERE idGenre = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Genre ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Delete record
	DELETE FROM DVDLibrary.Genre WHERE idGenre = inID;

	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_delete_language_by_id
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_delete_language_by_id`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_delete_language_by_id`(IN inID INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idLanguage FROM dvdlibrary.Language WHERE idLanguage = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Language ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Delete record
	DELETE FROM DVDLibrary.Language WHERE idLanguage = inID;

	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_delete_movie_by_id
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_delete_movie_by_id`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_delete_movie_by_id`(IN inID INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idMovie FROM dvdlibrary.Movie WHERE idMovie = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Movie ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Delete record
	DELETE FROM DVDLibrary.Movie WHERE idMovie = inID;

	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_delete_rating_by_id
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_delete_rating_by_id`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_delete_rating_by_id`(IN inID INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idRating FROM dvdlibrary.Rating WHERE idRating = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Rating ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Delete record
	DELETE FROM DVDLibrary.Rating WHERE idRating = inID;

	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_update_audio
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_update_audio`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_update_audio`(IN inID INT,
															IN inAudio VARCHAR(45))
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idAudio FROM dvdlibrary.Audio WHERE idAudio = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Audio ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Update record
	UPDATE Audio
	SET audio = inAudio
	WHERE idAudio = inID;

	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_update_dvd
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_update_dvd`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_update_dvd`(IN inID INT,
															IN inMovieId INT,
															IN inISBN VARCHAR(45),
															IN inEdition VARCHAR(45),
															IN inScreenFormat VARCHAR(45),
															IN inRegion VARCHAR(45))
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the DVD ID exists
	SELECT EXISTS(SELECT idDVD FROM dvdlibrary.DVD WHERE idDVD = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The DVD ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	#Check if the Movie ID exists
	SELECT EXISTS(SELECT idMovie FROM dvdlibrary.Movie WHERE idMovie = inMovieId) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Movie ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Update record
	UPDATE DVD
	SET movieId = inMovieId,
		edition = inEdition,
		ISBN = inISBN,
		screenFormat = inScreenFormat,
		region = inRegion
	WHERE idDVD = inID;
	
	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_update_genre
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_update_genre`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_update_genre`(IN inID INT,
															IN inGenre VARCHAR(45))
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idGenre FROM dvdlibrary.Genre WHERE idGenre = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Genre ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Update record
	UPDATE Genre
	SET genre = inGenre
	WHERE idGenre = inID;
	
	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_update_language
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_update_language`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_update_language`(IN inID INT,
															IN inLanguage VARCHAR(45))
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idLanguage FROM dvdlibrary.Language WHERE idLanguage = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Language ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Update record
	UPDATE Language
	SET language = inLanguage
	WHERE idLanguage = inID;
	
	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_update_movie
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_update_movie`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_update_movie`(IN inID INT,
															IN inTitle VARCHAR(45),
															IN inLength INT,
															IN inReleaseDate DATE,
															IN inIMDB INT,
															IN inRating INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;

	#Check if the Movie ID exists
	SELECT EXISTS(SELECT idMovie FROM dvdlibrary.Movie WHERE idMovie = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Movie ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	#Check if the Rating exists
	SELECT EXISTS(SELECT idRating FROM dvdlibrary.Rating WHERE idRating = inRating) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Rating ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Update record
	UPDATE Movie
	SET title = inTitle,
		length = inLength,
		releaseDate = inReleaseDate,
		IMDB = inIMDB,
		rating = inRating
	WHERE idMovie = inID;
	
	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_update_rating
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_update_rating`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_update_rating`(IN inID INT,
															IN inRating VARCHAR(45))
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idRating FROM dvdlibrary.Rating WHERE idRating = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Rating ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Update record
	UPDATE Rating
	SET rating = inRating
	WHERE idRating = inID;
	
	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_view_audio_by_id
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_view_audio_by_id`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_view_audio_by_id`(IN inID INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idAudio FROM dvdlibrary.Audio WHERE idAudio = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Audio ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Retrieve the record
	SELECT * FROM DVDLibrary.Audio WHERE idAudio = inID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_view_dvd_by_id
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_view_dvd_by_id`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_view_dvd_by_id`(IN inID INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idDVD FROM dvdlibrary.DVD WHERE idDVD = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The DVD ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Retrieve the record
	SELECT *
	FROM view_all_dvds vad
	WHERE vad.idDVD = inID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_view_genre_by_id
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_view_genre_by_id`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_view_genre_by_id`(IN inID INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idGenre FROM dvdlibrary.Genre WHERE idGenre = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Genre ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Retrieve the record
	SELECT * FROM DVDLibrary.Genre WHERE idGenre = inID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_view_language_by_id
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_view_language_by_id`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_view_language_by_id`(IN inID INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idLanguage FROM dvdlibrary.Language WHERE idLanguage = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Language ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Retrieve the record
	SELECT * FROM DVDLibrary.Language WHERE idLanguage = inID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_view_movie_by_id
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_view_movie_by_id`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_view_movie_by_id`(IN inID INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idMovie FROM dvdlibrary.Movie WHERE idMovie = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Movie ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Retrieve the record
	SELECT *
	FROM view_all_movies vam
	WHERE vam.idMovie = inID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_view_rating_by_id
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_view_rating_by_id`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_view_rating_by_id`(IN inID INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idRating FROM dvdlibrary.Rating WHERE idRating = inID) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Rating ID doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Retrieve the record
	SELECT * FROM DVDLibrary.Rating WHERE idRating = inID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_view_dvd_by_movie_rating
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_view_dvd_by_movie_rating`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_view_dvd_by_movie_rating`(IN inRating INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idRating FROM dvdlibrary.Rating WHERE idRating = inRating) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Rating doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Retrieve the record
	SELECT *
	FROM view_all_dvds vad
	WHERE vad.rating = inRating;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_delete_dvd_by_movie_rating
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_delete_dvd_by_movie_rating`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_delete_dvd_by_movie_rating`(IN inRating INT)
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	#Check if the ID exists
	SELECT EXISTS(SELECT idRating FROM dvdlibrary.Rating WHERE idRating = inRating) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Rating doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Perform delete
	DELETE FROM DVD
	WHERE movieId IN (
			SELECT idMovie
			FROM Movie
			WHERE rating = inRating);

	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_view_dvd_by_language_audio
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_view_dvd_by_language_audio`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_view_dvd_by_language_audio`(IN inLanguage VARCHAR(45), IN inAudio VARCHAR(45))
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	
	#Check if the Language exists
	SELECT EXISTS(SELECT idLanguage FROM dvdlibrary.Language WHERE language = inLanguage) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Language doesn't exist in the database!";
		LEAVE body;
	END IF;

	#Check if the Audio exists
	SELECT EXISTS(SELECT idAudio FROM dvdlibrary.Audio WHERE audio = inAudio) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Audio doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Perform Query
	SELECT * FROM view_all_dvds
	WHERE Languages LIKE CONCAT("%" , inLanguage, "%")
		AND Audios LIKE CONCAT("%", inAudio, "%");
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure usp_delete_dvd_by_language_audio
-- -----------------------------------------------------

USE `DVDLibrary`;
DROP procedure IF EXISTS `DVDLibrary`.`usp_delete_dvd_by_language_audio`;

DELIMITER $$
USE `DVDLibrary`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_delete_dvd_by_language_audio`(IN inLanguage VARCHAR(45), IN inAudio VARCHAR(45))
body:
BEGIN
	DECLARE checkExists INT DEFAULT 0;
	
	#Check if the Language exists
	SELECT EXISTS(SELECT idLanguage FROM dvdlibrary.Language WHERE language = inLanguage) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Language doesn't exist in the database!";
		LEAVE body;
	END IF;

	#Check if the Audio exists
	SELECT EXISTS(SELECT idAudio FROM dvdlibrary.Audio WHERE audio = inAudio) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "The Audio doesn't exist in the database!";
		LEAVE body;
	END IF;

	#Check if a DVD with that Language/Audio Pair exists
	SELECT EXISTS(
		SELECT dhla.DVD_idDVD
		FROM Language l, Audio a, dvd_has_language_audio dhla
		WHERE l.idLanguage = dhla.Language_idLanguage
			AND a.idAudio = dhla.Audio_idAudio
			AND l.language = inLanguage
			AND a.audio = inAudio) INTO checkExists;

	IF (checkExists = 0) THEN
		SELECT -1, "A DVD with the given Language and Audio doesn't exist in the database!";
		LEAVE body;
	END IF;

	# Perform Action
	DELETE FROM DVD
	WHERE idDVD IN (
		SELECT dhla.DVD_idDVD
		FROM Language l, Audio a, dvd_has_language_audio dhla
		WHERE l.idLanguage = dhla.Language_idLanguage
			AND a.idAudio = dhla.Audio_idAudio
			AND l.language = inLanguage
			AND a.audio = inAudio);

	# Return Status Code and Message
	SELECT 0, "Success";
END$$

DELIMITER ;

-- -----------------------------------------------------
-- View `DVDLibrary`.`view_all_movies`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `DVDLibrary`.`view_all_movies` ;
DROP TABLE IF EXISTS `DVDLibrary`.`view_all_movies`;
USE `DVDLibrary`;
CREATE  OR REPLACE VIEW `view_all_movies` AS
SELECT m.idMovie, m.title, m.length, m.releaseDate, m.IMDB, m.rating, group_concat(g.idGenre) AS GenreIDs, group_concat(g.Genre) AS Genres
FROM Movie m, Genre g, movie_has_genre mhg
WHERE m.idMovie = mhg.Movie_idMovie AND g.idGenre = mhg.Genre_idGenre
GROUP BY m.idMovie;

-- -----------------------------------------------------
-- View `DVDLibrary`.`view_all_audios`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `DVDLibrary`.`view_all_audios` ;
DROP TABLE IF EXISTS `DVDLibrary`.`view_all_audios`;
USE `DVDLibrary`;
CREATE  OR REPLACE VIEW `view_all_audios` AS
SELECT * FROM dvdlibrary.audio;

-- -----------------------------------------------------
-- View `DVDLibrary`.`view_all_languages`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `DVDLibrary`.`view_all_languages` ;
DROP TABLE IF EXISTS `DVDLibrary`.`view_all_languages`;
USE `DVDLibrary`;
CREATE  OR REPLACE VIEW `view_all_languages` AS
SELECT * FROM dvdlibrary.language;

-- -----------------------------------------------------
-- View `DVDLibrary`.`view_all_genres`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `DVDLibrary`.`view_all_genres` ;
DROP TABLE IF EXISTS `DVDLibrary`.`view_all_genres`;
USE `DVDLibrary`;
CREATE  OR REPLACE VIEW `view_all_genres` AS
SELECT * FROM dvdlibrary.Genre;

-- -----------------------------------------------------
-- View `DVDLibrary`.`view_all_dvds`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `DVDLibrary`.`view_all_dvds` ;
DROP TABLE IF EXISTS `DVDLibrary`.`view_all_dvds`;
USE `DVDLibrary`;
CREATE  OR REPLACE VIEW `view_all_dvds` AS
SELECT dvd.idDVD,
		vam.idMovie, vam.title, vam.length, vam.releaseDate, vam.IMDB, vam.rating, vam.GenreIds, vam.Genres,
		dvd.ISBN, dvd.edition, dvd.screenFormat, dvd.region,
		group_concat(l.idLanguage) AS LanguageIDs, group_concat(l.language) AS Languages, group_concat(a.idAudio) AS AudioIDs, group_concat(a.audio) AS Audios
FROM dvd, view_all_movies vam, language l, audio a, dvd_has_language_audio dhla
WHERE dvd.idDVD = dhla.DVD_idDVD
		AND dvd.movieId = vam.idMovie
		AND l.idLanguage = dhla.Language_idLanguage
		AND a.idAudio = dhla.Audio_idAudio
GROUP BY dvd.idDVD;

-- -----------------------------------------------------
-- View `DVDLibrary`.`view_all_ratings`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `DVDLibrary`.`view_all_ratings` ;
DROP TABLE IF EXISTS `DVDLibrary`.`view_all_ratings`;
USE `DVDLibrary`;
CREATE  OR REPLACE VIEW `view_all_ratings` AS
SELECT * FROM dvdlibrary.rating;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
