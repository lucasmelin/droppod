-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema droppod
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema droppod
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `droppod` DEFAULT CHARACTER SET utf8mb4 ;
USE `droppod` ;

-- -----------------------------------------------------
-- Table `droppod`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`categories` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`cities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`cities` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`countries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`countries` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`podcasts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`podcasts` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `thumbnail` MEDIUMBLOB NULL DEFAULT NULL,
  `url` VARCHAR(255) NOT NULL,
  `rating` INT(11) NULL DEFAULT NULL,
  `uri` VARCHAR(255) NOT NULL,
  `thumbnail_url` VARCHAR(255) NULL DEFAULT NULL,
  `uuid` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uri_UNIQUE` (`uri` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`episodes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`episodes` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `url` VARCHAR(255) NOT NULL,
  `podcast_id` INT(11) NOT NULL,
  `thumbnail` BLOB NULL DEFAULT NULL,
  `release_date` DATETIME NULL DEFAULT NULL,
  `network_id` INT(11) NULL DEFAULT NULL,
  `episode_number` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `podcast_id_idx` (`podcast_id` ASC),
  CONSTRAINT `fk_episodes_podcasts_podcast_id`
    FOREIGN KEY (`podcast_id`)
    REFERENCES `droppod`.`podcasts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`languages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`languages` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `code` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`episodes_translations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`episodes_translations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `episode_id` INT(11) NOT NULL,
  `language_code` VARCHAR(5) NOT NULL,
  `episode_name` VARCHAR(255) NULL DEFAULT NULL,
  `episode_description` MEDIUMTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_episodes_translations_languages_language_code_idx` (`language_code` ASC),
  INDEX `fk_episodes_translations_episodes_episode_id_idx` (`episode_id` ASC),
  CONSTRAINT `fk_episodes_translations_languages_language_code`
    FOREIGN KEY (`language_code`)
    REFERENCES `droppod`.`languages` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_episodes_translations_episodes_episode_id`
    FOREIGN KEY (`episode_id`)
    REFERENCES `droppod`.`episodes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `validated` TINYINT(4) NOT NULL,
  `profile_picture` BLOB NULL DEFAULT NULL,
  `country_id` INT(11) NULL DEFAULT NULL,
  `city_id` INT(11) NULL DEFAULT NULL,
  `language_id` INT(11) NULL DEFAULT NULL,
  `night_mode_enabled` TINYINT(4) NULL DEFAULT NULL,
  `active` TINYINT(4) NULL DEFAULT NULL,
  `account_type_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  INDEX `country_id_idx` (`country_id` ASC),
  INDEX `city_id_idx` (`city_id` ASC),
  INDEX `language_id_idx` (`language_id` ASC),
  CONSTRAINT `fk_users_cities_city_id`
    FOREIGN KEY (`city_id`)
    REFERENCES `droppod`.`cities` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_countries_country_id`
    FOREIGN KEY (`country_id`)
    REFERENCES `droppod`.`countries` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_languages_language_id`
    FOREIGN KEY (`language_id`)
    REFERENCES `droppod`.`languages` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`friends` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user1_id` INT(11) NOT NULL,
  `user2_id` INT(11) NOT NULL,
  `user1_accepted` TINYINT(4) NULL DEFAULT NULL,
  `user2_accepted` TINYINT(4) NULL DEFAULT NULL,
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
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`playlists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`playlists` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NULL DEFAULT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_playlists_users_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `droppod`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`playlist_episodes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`playlist_episodes` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `episode_id` INT(11) NULL DEFAULT NULL,
  `playlist_id` INT(11) NULL DEFAULT NULL,
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
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`podcast_categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`podcast_categories` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `podcast_id` INT(11) NOT NULL,
  `category_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `podcast_id_idx` (`podcast_id` ASC),
  INDEX `category_id_idx` (`category_id` ASC),
  CONSTRAINT `fk_podcast_categories_categories_category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `droppod`.`categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_podcast_categories_podcasts_podcast_id`
    FOREIGN KEY (`podcast_id`)
    REFERENCES `droppod`.`podcasts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`podcasts_translations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`podcasts_translations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `podcast_id` INT(11) NOT NULL,
  `language_code` VARCHAR(5) NOT NULL,
  `podcast_name` VARCHAR(255) NULL DEFAULT NULL,
  `podcast_description` MEDIUMTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_podcasts_translations_languages_language_code_idx` (`language_code` ASC),
  INDEX `fk_podcasts_translations_podcasts_podcast_id_idx` (`podcast_id` ASC),
  CONSTRAINT `fk_podcasts_translations_languages_language_code`
    FOREIGN KEY (`language_code`)
    REFERENCES `droppod`.`languages` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_podcasts_translations_podcasts_podcast_id`
    FOREIGN KEY (`podcast_id`)
    REFERENCES `droppod`.`podcasts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `droppod`.`subscriptions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `droppod`.`subscriptions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `podcast_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
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
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

USE `droppod`;

DELIMITER $$
USE `droppod`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `droppod`.`podcasts_BEFORE_INSERT`
BEFORE INSERT ON `droppod`.`podcasts`
FOR EACH ROW
BEGIN
 SET new.uuid = uuid();
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
