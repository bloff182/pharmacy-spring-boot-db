-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema apteczka
-- -----------------------------------------------------
drop schema if exists `apteczka`;
-- -----------------------------------------------------
-- Schema apteczka
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `apteczka` DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci ;
USE `apteczka` ;

-- -----------------------------------------------------
-- Table `apteczka`.`destiny`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `apteczka`.`destiny` (
  `id_destiny` INT(11) NOT NULL AUTO_INCREMENT,
  `name_destiny` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_destiny`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `apteczka`.`producent`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `apteczka`.`producent` (
  `id_producent` INT(11) NOT NULL,
  `name_producent` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_producent`),
  UNIQUE INDEX `nameProducent_UNIQUE` (`name_producent` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `apteczka`.`type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `apteczka`.`type` (
  `id_type` INT(11) NOT NULL AUTO_INCREMENT,
  `name_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_type`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `apteczka`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `apteczka`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` CHAR(80) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `apteczka`.`medicine`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `apteczka`.`medicine` (
  `id_medicine` INT(11) NOT NULL AUTO_INCREMENT,
  `name_medicine` VARCHAR(45) NOT NULL,
  `exp_date` DATE NOT NULL,
  `quantity` INT(11) NOT NULL,
  `Producent_id_producent` INT(11) NOT NULL,
  `Destiny_id_destiny` INT(11) NOT NULL,
  `Type_id_type` INT(11) NOT NULL,
  `comments` VARCHAR(45) NULL DEFAULT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id_medicine`, `user_id`),
  INDEX `fk_Medicines_Producent_idx` (`Producent_id_producent` ASC) VISIBLE,
  INDEX `fk_Medicines_Destiny1_idx` (`Destiny_id_destiny` ASC) VISIBLE,
  INDEX `fk_Medicines_Type1_idx` (`Type_id_type` ASC) VISIBLE,
  INDEX `fk_medicine_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_Medicines_Destiny1`
    FOREIGN KEY (`Destiny_id_destiny`)
    REFERENCES `apteczka`.`destiny` (`id_destiny`),
  CONSTRAINT `fk_Medicines_Producent`
    FOREIGN KEY (`Producent_id_producent`)
    REFERENCES `apteczka`.`producent` (`id_producent`),
  CONSTRAINT `fk_Medicines_Type1`
    FOREIGN KEY (`Type_id_type`)
    REFERENCES `apteczka`.`type` (`id_type`),
  CONSTRAINT `fk_medicine_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `apteczka`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `apteczka`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `apteczka`.`role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `apteczka`.`users_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `apteczka`.`users_roles` (
  `role_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`role_id`, `user_id`),
  INDEX `fk_role_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_role_has_user_role_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_role_has_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `apteczka`.`role` (`id`),
  CONSTRAINT `fk_role_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `apteczka`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `apteczka`.`destiny` (`id_destiny`, `name_destiny`) VALUES ('1', 'dorosli');
INSERT INTO `apteczka`.`destiny` (`id_destiny`, `name_destiny`) VALUES ('2', 'dzieci');
INSERT INTO `apteczka`.`destiny` (`id_destiny`, `name_destiny`) VALUES ('3', 'wszyscy');

INSERT INTO `apteczka`.`producent` (`id_producent`, `name_producent`) VALUES ('1', 'Jelfa');
INSERT INTO `apteczka`.`producent` (`id_producent`, `name_producent`) VALUES ('2', 'Bayer');
INSERT INTO `apteczka`.`producent` (`id_producent`, `name_producent`) VALUES ('3', 'Hasco-Lek');

INSERT INTO `apteczka`.`type` (`id_type`, `name_type`) VALUES ('1', 'tabletki');
INSERT INTO `apteczka`.`type` (`id_type`, `name_type`) VALUES ('2', 'krople');
INSERT INTO `apteczka`.`type` (`id_type`, `name_type`) VALUES ('3', 'czopki');
INSERT INTO `apteczka`.`type` (`id_type`, `name_type`) VALUES ('4', 'aerozol');
INSERT INTO `apteczka`.`type` (`id_type`, `name_type`) VALUES ('5', 'ampluki');
INSERT INTO `apteczka`.`type` (`id_type`, `name_type`) VALUES ('6', 'syrop');
INSERT INTO `apteczka`.`type` (`id_type`, `name_type`) VALUES ('7', 'saszetki');
INSERT INTO `apteczka`.`type` (`id_type`, `name_type`) VALUES ('8', 'roztwor');
INSERT INTO `apteczka`.`type` (`id_type`, `name_type`) VALUES ('9', 'tabletki musujace');

INSERT INTO `apteczka`.`role` (`id`, `name`) VALUES ('1', 'ROLE_EMPLOYEE');
INSERT INTO `apteczka`.`role` (`id`, `name`) VALUES ('2', 'ROLE_MANAGER');
INSERT INTO `apteczka`.`role` (`id`, `name`) VALUES ('3', 'ROLE_ADMIN');


-- password` fun123
INSERT INTO `apteczka`.`user` (`id`, `username`, `password`, `first_name`, `last_name`, `email`) VALUES ('1', 'jan', '$2a$10$Rhu8izvgyC07RM/WZsMhleTaL3afxLMArArlj0Mb7zK2fInjKA2/i', 'jan', 'nowak', 'j.nowak@bloff.com');
INSERT INTO `apteczka`.`user` (`id`, `username`, `password`, `first_name`, `last_name`, `email`) VALUES ('2', 'maria', '$2a$10$Rhu8izvgyC07RM/WZsMhleTaL3afxLMArArlj0Mb7zK2fInjKA2/i', 'maria', 'kawa', 'm.kawa@bloff.com');
INSERT INTO `apteczka`.`user` (`id`, `username`, `password`, `first_name`, `last_name`, `email`) VALUES ('3', 'anna', '$2a$10$Rhu8izvgyC07RM/WZsMhleTaL3afxLMArArlj0Mb7zK2fInjKA2/i', 'anna', 'kowalska', 'a.kowalska@bloff.com');

INSERT INTO `apteczka`.`users_roles` (`role_id`, `user_id`) VALUES ('1', '1');
INSERT INTO `apteczka`.`users_roles` (`role_id`, `user_id`) VALUES ('2', '1');
INSERT INTO `apteczka`.`users_roles` (`role_id`, `user_id`) VALUES ('2', '2');
INSERT INTO `apteczka`.`users_roles` (`role_id`, `user_id`) VALUES ('3', '1');
INSERT INTO `apteczka`.`users_roles` (`role_id`, `user_id`) VALUES ('3', '3');

INSERT INTO `apteczka`.`medicine` (`id_medicine`, `name_medicine`, `exp_date`, `quantity`, `Producent_id_producent`, `Destiny_id_destiny`, `Type_id_type`, `user_id`) VALUES ('1', 'Vitaral', '2020-12-29', '1', '1', '1', '1', '1');
INSERT INTO `apteczka`.`medicine` (`id_medicine`, `name_medicine`, `exp_date`, `quantity`, `Producent_id_producent`, `Destiny_id_destiny`, `Type_id_type`, `user_id`) VALUES ('2', 'Orofar', '2021-07-15', '2', '2', '3', '2', '1');
INSERT INTO `apteczka`.`medicine` (`id_medicine`, `name_medicine`, `exp_date`, `quantity`, `Producent_id_producent`, `Destiny_id_destiny`, `Type_id_type`, `user_id`) VALUES ('3', 'Vit-d3', '2021-12-12', '3', '2', '2', '3', '2');

