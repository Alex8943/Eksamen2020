-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema projektoplysninger
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema projektoplysninger
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projektoplysninger` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `projektoplysninger` ;

-- -----------------------------------------------------
-- Table `projektoplysninger`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektoplysninger`.`user` (
                                                           `id` INT NOT NULL AUTO_INCREMENT,
                                                           `mail` VARCHAR(45) NOT NULL,
                                                           `password` VARCHAR(45) NOT NULL,
                                                           `isAdmin` INT NOT NULL DEFAULT '0',
                                                           `adminID` INT NULL DEFAULT '0',
                                                           PRIMARY KEY (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 12
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `projektoplysninger`.`projects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektoplysninger`.`projects` (
                                                               `id` INT NOT NULL AUTO_INCREMENT,
                                                               `name` VARCHAR(45) NULL DEFAULT NULL,
                                                               `description` VARCHAR(16000) NULL DEFAULT NULL,
                                                               `numberOfEmployees` INT NULL DEFAULT NULL,
                                                               `saved` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                                               `deadline` DATE NULL DEFAULT NULL,
                                                               `userID` INT NULL DEFAULT NULL,
                                                               PRIMARY KEY (`id`),
                                                               UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                                                               INDEX `user_idx` (`userID` ASC) VISIBLE,
                                                               CONSTRAINT `user`
                                                                   FOREIGN KEY (`userID`)
                                                                       REFERENCES `projektoplysninger`.`user` (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `projektoplysninger`.`subprojects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektoplysninger`.`subprojects` (
                                                                  `subId` INT NOT NULL AUTO_INCREMENT,
                                                                  `subName` VARCHAR(45) NULL DEFAULT NULL,
                                                                  `subDescription` VARCHAR(16000) NULL DEFAULT NULL,
                                                                  `projectID` INT NOT NULL,
                                                                  `estimatedTime` INT NULL DEFAULT NULL,
                                                                  PRIMARY KEY (`subId`),
                                                                  UNIQUE INDEX `id_UNIQUE` (`subId` ASC) VISIBLE,
                                                                  INDEX `projectID_idx` (`projectID` ASC) VISIBLE,
                                                                  CONSTRAINT `projectID`
                                                                      FOREIGN KEY (`projectID`)
                                                                          REFERENCES `projektoplysninger`.`projects` (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 13
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `projektoplysninger`.`tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektoplysninger`.`tasks` (
                                                            `taskID` INT NOT NULL AUTO_INCREMENT,
                                                            `taskName` VARCHAR(45) NOT NULL,
                                                            `taskDes` VARCHAR(45) NOT NULL,
                                                            `projectIDTask` INT NOT NULL,
                                                            `taskDeadline` DATE NOT NULL,
                                                            PRIMARY KEY (`taskID`),
                                                            UNIQUE INDEX `idtasks_UNIQUE` (`taskID` ASC) VISIBLE,
                                                            INDEX `projectIDTask_idx` (`projectIDTask` ASC) VISIBLE,
                                                            CONSTRAINT `projectIDTask`
                                                                FOREIGN KEY (`projectIDTask`)
                                                                    REFERENCES `projektoplysninger`.`projects` (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
