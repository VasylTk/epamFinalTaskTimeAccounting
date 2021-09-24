
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema timeaccounting
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `timeaccounting` ;

-- -----------------------------------------------------
-- Schema timeaccounting
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `timeaccounting` DEFAULT CHARACTER SET utf8 ;
USE `timeaccounting` ;

-- -----------------------------------------------------
-- Table `timeaccounting`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `timeaccounting`.`account` ;

CREATE TABLE IF NOT EXISTS `timeaccounting`.`account` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_login` VARCHAR(32) NOT NULL,
    `user_password` VARCHAR(32) NOT NULL,
    `user_role` INT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `user_login_UNIQUE` (`user_login` ASC) VISIBLE,
    INDEX `fk_account_user_role_idx` (`user_role` ASC) VISIBLE,
    CONSTRAINT `fk_account_user_role`
    FOREIGN KEY (`user_role`)
    REFERENCES `timeaccounting`.`user_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    AUTO_INCREMENT = 20
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `timeaccounting`.`activity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `timeaccounting`.`activity` ;

CREATE TABLE IF NOT EXISTS `timeaccounting`.`activity` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name_activity` VARCHAR(45) NOT NULL,
    `id_category` INT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_activity_category1_idx` (`id_category` ASC) VISIBLE,
    CONSTRAINT `fk_activity_category1`
    FOREIGN KEY (`id_category`)
    REFERENCES `timeaccounting`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    AUTO_INCREMENT = 144
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `timeaccounting`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `timeaccounting`.`category` ;

CREATE TABLE IF NOT EXISTS `timeaccounting`.`category` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name_category` VARCHAR(45) NOT NULL,
    `id_parent` INT NULL DEFAULT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 21
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `timeaccounting`.`user_activities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `timeaccounting`.`user_activities` ;

CREATE TABLE IF NOT EXISTS `timeaccounting`.`user_activities` (
     `id` INT NOT NULL AUTO_INCREMENT,
     `id_user` INT NOT NULL,
     `id_activity` INT NOT NULL,
     `spent_time` TIME NULL DEFAULT NULL,
     `state` INT NULL DEFAULT '1',
     `on_delete` INT NULL DEFAULT '0',
      PRIMARY KEY (`id`),
    INDEX `fk_user_activities_account1_idx` (`id_user` ASC) VISIBLE,
    INDEX `fk_user_activities_activity1_idx` (`id_activity` ASC) VISIBLE,
    CONSTRAINT `fk_user_activities_account1`
    FOREIGN KEY (`id_user`)
    REFERENCES `timeaccounting`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_activities_activity1`
    FOREIGN KEY (`id_activity`)
    REFERENCES `timeaccounting`.`activity` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    AUTO_INCREMENT = 263
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `timeaccounting`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `timeaccounting`.`user_role` ;

CREATE TABLE IF NOT EXISTS `timeaccounting`.`user_role` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name_user_role` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `user_role_name_UNIQUE` (`id` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 6
    DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- -----------------------------------------------------
-- Test Data
-- -----------------------------------------------------


-- -----------------------------------------------------
-- Insering user_role table
-- -----------------------------------------------------
INSERT INTO user_role VALUES(0, "admin");
INSERT INTO user_role VALUES(1, "user");


-- -----------------------------------------------------
-- Insering account table
-- -----------------------------------------------------
INSERT INTO account VALUES(default, "admin@gmail.com", "password", 0);
INSERT INTO account VALUES(default, "user@gmail.com", "password", 1);
INSERT INTO account VALUES(default, "ivan@gmail.com", "password", 1);
INSERT INTO account VALUES(default, "vasyl@gmail.com", "password", 1);
INSERT INTO account VALUES(default, "alex@gmail.com", "password", 1);
INSERT INTO account VALUES(default, "dan@gmail.com", "password", 1);
INSERT INTO account VALUES(default, "max@gmail.com", "password", 1);


-- -----------------------------------------------------
-- Insering category table
-- -----------------------------------------------------
INSERT INTO category VALUES(default, "bugs", 0);
INSERT INTO category VALUES(default, "new features", 0);
INSERT INTO category VALUES(default, "buttons", 0);
INSERT INTO category VALUES(default, "games", 0);
INSERT INTO category VALUES(default, "fixing", 0);
INSERT INTO category VALUES(default, "adding", 0);

-- -----------------------------------------------------
-- Insering activity table
-- -----------------------------------------------------
INSERT INTO activity VALUES(default, "change the text", 1);
INSERT INTO activity VALUES(default, "change the color", 1);
INSERT INTO activity VALUES(default, "add the button", 2);
INSERT INTO activity VALUES(default, "add a radio button", 2);
INSERT INTO activity VALUES(default, "add a new interface", 2);
INSERT INTO activity VALUES(default, "add a slider", 2);\
INSERT INTO activity VALUES(default, "change the style of the app", 2);


-- -----------------------------------------------------
-- Insering user_activities table
-- -----------------------------------------------------
INSERT INTO user_activities VALUES(default, 2 , 1, 0, 1, 0);
INSERT INTO user_activities VALUES(default, 2 , 2, 0, 1, 0);
INSERT INTO user_activities VALUES(default, 2 , 3, 0, 1, 0);
INSERT INTO user_activities VALUES(default, 2 , 4, 0, 1, 0);
INSERT INTO user_activities VALUES(default, 2 , 5, 0, 1, 0);
INSERT INTO user_activities VALUES(default, 2 , 6, 0, 1, 0);
INSERT INTO user_activities VALUES(default, 2 , 7, 0, 1, 0);



