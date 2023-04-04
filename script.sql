-- MySQL Script generated by MySQL Workbench
-- Tue Dec 13 23:23:31 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema kp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema kp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kp` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `kp` ;

-- -----------------------------------------------------
-- Table `kp`.`keys`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kp`.`keys` (
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` INT NOT NULL,
  PRIMARY KEY (`login`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kp`.`accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kp`.`accounts` (
  `login` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `department` VARCHAR(45) NOT NULL,
  `position` VARCHAR(45) NOT NULL,
  `num_docs` INT NOT NULL,
  PRIMARY KEY (`login`),
  INDEX `fk_surname_name_idx` (`surname` ASC, `name` ASC) VISIBLE,
  INDEX `fk_num_docs_idx` (`num_docs` ASC) INVISIBLE,
  INDEX `fk_department_idx` (`department` ASC) VISIBLE,
  CONSTRAINT `fk_keys_login`
    FOREIGN KEY (`login`)
    REFERENCES `kp`.`keys` (`login`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kp`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kp`.`account` (
  `login` VARCHAR(45) NOT NULL,
  `num_docs` INT NOT NULL,
  `name_docs` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`login`),
  INDEX `fk_num_docs_idx` (`num_docs` ASC) VISIBLE,
  INDEX `fk_accounts_num_docs_idx` (`num_docs` ASC) VISIBLE,
  CONSTRAINT `fk_accounts_num_docs`
    FOREIGN KEY (`num_docs`)
    REFERENCES `kp`.`accounts` (`num_docs`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_keys_login1`
    FOREIGN KEY (`login`)
    REFERENCES `kp`.`keys` (`login`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kp`.`doc1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kp`.`doc1` (
  `name` VARCHAR(45) NOT NULL,
  `worker` VARCHAR(45) NULL DEFAULT NULL,
  `company` VARCHAR(45) NOT NULL,
  `reg_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `doc` VARCHAR(300) NOT NULL,
  `status` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`name`),
  INDEX `fk_keys_worker_idx` (`worker` ASC) VISIBLE,
  CONSTRAINT `fk_keys_worker`
    FOREIGN KEY (`worker`)
    REFERENCES `kp`.`keys` (`login`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kp`.`doc2`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kp`.`doc2` (
  `name` VARCHAR(45) NOT NULL,
  `department` VARCHAR(45) NOT NULL,
  `date` DATE NOT NULL,
  `document` VARCHAR(300) NOT NULL,
  `status` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kp`.`heads`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kp`.`heads` (
  `login` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `department` VARCHAR(45) NOT NULL,
  `num_workers` INT NOT NULL,
  `num_docs` INT NOT NULL,
  PRIMARY KEY (`login`),
  INDEX `fk_surname_name_idx` (`surname` ASC, `name` ASC) INVISIBLE,
  CONSTRAINT `fk_keys_login2`
    FOREIGN KEY (`login`)
    REFERENCES `kp`.`keys` (`login`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_surname_name`
    FOREIGN KEY (`surname` , `name`)
    REFERENCES `kp`.`accounts` (`surname` , `name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
