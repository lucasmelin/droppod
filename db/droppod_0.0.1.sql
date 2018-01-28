-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema droppod
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema droppod
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `droppod` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema new_schema1
-- -----------------------------------------------------
USE `droppod` ;

-- -----------------------------------------------------
-- Table `droppod`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`users` (
  `id` INT NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`podcasts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`podcasts` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` MEDIUMTEXT NULL,
  `thumbnail` BLOB NULL,
  `url` VARCHAR(45) NOT NULL,
  `rating` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`episodes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`episodes` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `podcast_id` INT NOT NULL,
  `description` MEDIUMTEXT NULL,
  `thumbnail` BLOB NULL,
  `release_date` DATETIME NULL,
  `network_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `podcast_id_idx` (`podcast_id` ASC),
  CONSTRAINT `podcast_id`
    FOREIGN KEY (`podcast_id`)
    REFERENCES `droppod`.`podcasts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`playlists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`playlists` (
  `id` INT NOT NULL,
  `user_id` INT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `droppod`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`playlists_epsiodes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`playlists_epsiodes` (
  `id` INT NOT NULL,
  `episode_id` INT NULL,
  `playlist_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `episode_id_idx` (`episode_id` ASC),
  INDEX `playlist_id_idx` (`playlist_id` ASC),
  CONSTRAINT `episode_id`
    FOREIGN KEY (`episode_id`)
    REFERENCES `droppod`.`episodes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `playlist_id`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `droppod`.`playlists` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`categories` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`podcast_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`podcast_category` (
  `id` INT NOT NULL,
  `podcast_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `podcast_id_idx` (`podcast_id` ASC),
  INDEX `category_id_idx` (`category_id` ASC),
  CONSTRAINT `podcast_id`
    FOREIGN KEY (`podcast_id`)
    REFERENCES `droppod`.`podcasts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `droppod`.`categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
