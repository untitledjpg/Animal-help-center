CREATE TABLE `animalhelpcenter`.`temp_home`
(
    `temp_home_id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(60)  NOT NULL,
    `surname` VARCHAR(100) NOT NULL,
    `phone` INT NOT NULL,
    `email` VARCHAR(300) NOT NULL,
    `address` VARCHAR(300) NOT NULL,
    PRIMARY KEY (`temp_home_id`)
);