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
-- Table `droppod`.`countries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`countries` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`cities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`cities` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`languages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`languages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `validated` TINYINT NOT NULL,
  `profile_picture` BLOB NULL,
  `country_id` INT NULL,
  `city_id` INT NULL,
  `language_id` INT NULL,
  `night_mode_enabled` TINYINT NULL,
  `active` TINYINT NULL,
  `account_type_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  INDEX `country_id_idx` (`country_id` ASC),
  INDEX `city_id_idx` (`city_id` ASC),
  INDEX `language_id_idx` (`language_id` ASC),
  CONSTRAINT `fk_users_countries_country_id`
    FOREIGN KEY (`country_id`)
    REFERENCES `droppod`.`countries` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_cities_city_id`
    FOREIGN KEY (`city_id`)
    REFERENCES `droppod`.`cities` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_languages_language_id`
    FOREIGN KEY (`language_id`)
    REFERENCES `droppod`.`languages` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`podcasts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`podcasts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` MEDIUMTEXT NULL,
  `thumbnail` BLOB NULL,
  `url` VARCHAR(45) NOT NULL,
  `rating` INT NULL,
  `uri` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uri_UNIQUE` (`uri` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`episodes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`episodes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `podcast_id` INT NOT NULL,
  `description` MEDIUMTEXT NULL,
  `thumbnail` BLOB NULL,
  `release_date` DATETIME NULL,
  `network_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `podcast_id_idx` (`podcast_id` ASC),
  CONSTRAINT `fk_episodes_podcasts_podcast_id`
    FOREIGN KEY (`podcast_id`)
    REFERENCES `droppod`.`podcasts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`playlists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`playlists` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_playlists_users_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `droppod`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`playlist_episodes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`playlist_episodes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `episode_id` INT NULL,
  `playlist_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `episode_id_idx` (`episode_id` ASC),
  INDEX `playlist_id_idx` (`playlist_id` ASC),
  CONSTRAINT `fk_playlist_episodes_episodes_episode_id`
    FOREIGN KEY (`episode_id`)
    REFERENCES `droppod`.`episodes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_playlist_episodes_playlists_playlist_id`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `droppod`.`playlists` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`podcast_categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`podcast_categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `podcast_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `podcast_id_idx` (`podcast_id` ASC),
  INDEX `category_id_idx` (`category_id` ASC),
  CONSTRAINT `fk_podcast_categories_podcasts_podcast_id`
    FOREIGN KEY (`podcast_id`)
    REFERENCES `droppod`.`podcasts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_podcast_categories_categories_category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `droppod`.`categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`subscriptions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`subscriptions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `podcast_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `podcast_id_idx` (`podcast_id` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_subscriptions_podcasts_podcast_id`
    FOREIGN KEY (`podcast_id`)
    REFERENCES `droppod`.`podcasts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subscriptions_users_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `droppod`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `droppod`.`friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`friends` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user1_id` INT NOT NULL,
  `user2_id` INT NOT NULL,
  `user1_accepted` TINYINT NULL,
  `user2_accepted` TINYINT NULL,
  PRIMARY KEY (`id`),
  INDEX `user1_id_idx` (`user1_id` ASC),
  INDEX `user2_id_idx` (`user2_id` ASC),
  CONSTRAINT `fk_friends_users_user1_id`
    FOREIGN KEY (`user1_id`)
    REFERENCES `droppod`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_friends_users_user2_id`
    FOREIGN KEY (`user2_id`)
    REFERENCES `droppod`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
