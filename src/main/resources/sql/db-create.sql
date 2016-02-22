CREATE TABLE RATING (
  ID INT NOT NULL GENERATED BY DEFAULT AS IDENTITY(START WITH 1),
  NAME VARCHAR(45) NOT NULL,
  PRIMARY KEY (ID));

CREATE TABLE GENRE (
  ID INT NOT NULL GENERATED BY DEFAULT AS IDENTITY(START WITH 1),
  NAME VARCHAR(45) NOT NULL,
  PRIMARY KEY (ID));

CREATE TABLE LANGUAGE (
  ID INT NOT NULL GENERATED BY DEFAULT AS IDENTITY(START WITH 1),
  NAME VARCHAR(45) NOT NULL,
  PRIMARY KEY (ID));

CREATE TABLE AUDIO (
  ID INT NOT NULL GENERATED BY DEFAULT AS IDENTITY(START WITH 1),
  NAME VARCHAR(45) NOT NULL,
  PRIMARY KEY (ID));

CREATE TABLE MOVIE (
  ID INT NOT NULL GENERATED BY DEFAULT AS IDENTITY(START WITH 1),
  TITLE VARCHAR(45) NOT NULL,
  LENGTH INT NOT NULL,
  REALEASEDATE DATE,
  IMDB INT,
  RATING INT,
PRIMARY KEY (ID),
CONSTRAINT FK_RATING FOREIGN KEY (RATING) REFERENCES RATING(ID));

CREATE TABLE DVD (
  ID INT NOT NULL GENERATED BY DEFAULT AS IDENTITY(START WITH 1),
  MOVIE_ID INT NOT NULL,
  ISBN VARCHAR(45) NOT NULL,
  EDITION VARCHAR(45) NULL,
  SCREEN_FORMAT VARCHAR(10) NULL,
  REGION VARCHAR(45) NULL,
PRIMARY KEY (ID),
CONSTRAINT FK_MOVIE FOREIGN KEY (MOVIE_ID) REFERENCES MOVIE(ID));