CREATE TABLE `animalhelpcenter`.`adoption_app`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(60) NOT NULL,
    `surname` VARCHAR(100) NOT NULL,
    `phone_number` INT NOT NULL,
    `email` VARCHAR(300) NOT NULL,
    `other_pets` VARCHAR(60) NOT NULL,
    `children` VARCHAR(60) NOT NULL,
    `cat_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `cat_id_fk_idx` (`cat_id` ASC) VISIBLE,
    CONSTRAINT `cat_id_fk`
        FOREIGN KEY (`cat_id`)
            REFERENCES `animalhelpcenter`.`cat` (`cat_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
