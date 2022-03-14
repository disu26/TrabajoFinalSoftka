-- -----------------------------------------------------
-- Schema Bingo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS bingo DEFAULT CHARACTER SET utf8 ;
USE bingo ;

-- -----------------------------------------------------
-- Table game
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS game (
  gam_id INT NOT NULL AUTO_INCREMENT,
  gam_in_progress TINYINT(1) NULL,
  gam_started TINYINT(1) NULL,
  gam_finished TINYINT(1) NULL,
  gam_start_timer DATETIME NULL,
  PRIMARY KEY (gam_id)
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table card
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS card (
  car_id INT NOT NULL AUTO_INCREMENT,
  car_winner TINYINT(1) NULL,
  PRIMARY KEY (car_id)
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table user
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user (
  use_id INT NOT NULL AUTO_INCREMENT,
  use_mongo_id VARCHAR(90) NOT NULL,
  game_gam_id INT NOT NULL,
  card_car_id INT NOT NULL,
  use_winner TINYINT(1) NULL,
  use_admin TINYINT(1) NULL,
  PRIMARY KEY (use_id),
  UNIQUE INDEX use_game_mongoid_UNIQUE (use_mongo_id ASC, game_gam_id ASC) VISIBLE,
  INDEX fk_user_game1_idx (game_gam_id ASC) VISIBLE,
  INDEX fk_user_card1_idx (card_car_id ASC) VISIBLE,
  CONSTRAINT fk_user_game1
    FOREIGN KEY (game_gam_id)
    REFERENCES game (gam_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_user_card1
    FOREIGN KEY (card_car_id)
    REFERENCES card (car_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table ballot
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ballot (
  bal_id INT NOT NULL AUTO_INCREMENT,
  bal_letter VARCHAR(1) NOT NULL,
  bal_number VARCHAR(2) NOT NULL,
  bal_out TINYINT(1) NULL,
  PRIMARY KEY (bal_id)
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table card_has_ballot
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS card_has_ballot (
  car_bal_id INT NOT NULL AUTO_INCREMENT,
  card_car_id INT NOT NULL,
  ballot_bal_id INT NOT NULL,
  card_bal_marked TINYINT(1) NULL,
  PRIMARY KEY (car_bal_id),
  INDEX fk_card_has_ballot_ballot1_idx (ballot_bal_id ASC) VISIBLE,
  INDEX fk_card_has_ballot_card1_idx (card_car_id ASC) VISIBLE,
  CONSTRAINT fk_card_has_ballot_card1
    FOREIGN KEY (card_car_id)
    REFERENCES card (car_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_card_has_ballot_ballot1
    FOREIGN KEY (ballot_bal_id)
    REFERENCES ballot (bal_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;
