CREATE TABLE `animalhelpcenter`.`volunteer`
(
    `volunteer_id` INT NOT NULL AUTO_INCREMENT,
    `volunteer_name` VARCHAR(60)  NOT NULL,
    `volunteer_surname` VARCHAR(60) NOT NULL,
    `volunteer_phone` INT NOT NULL,
    `volunteer_email` VARCHAR(60) NOT NULL,
    PRIMARY KEY (`volunteer_id`)
);