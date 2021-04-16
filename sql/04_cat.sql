CREATE TABLE `animalhelpcenter`.`cat`
(
    `cat_id` INT NOT NULL AUTO_INCREMENT,
    `cat_name` VARCHAR(60) NOT NULL,
    `cat_age` INT NOT NULL,
    `cat_color` VARCHAR(60) NOT NULL,
    `cat_sex` VARCHAR(10) NOT NULL,
    `neutered` VARCHAR(10) NOT NULL,
    `description` VARCHAR(3000) NOT NULL,
    `picture_path` VARCHAR(300) NOT NULL,
    `status` VARCHAR(100) NOT NULL,
    `cat_arrival`  DATE NOT NULL,
    `temp_home_id` INT NOT NULL,
    `volunteer_id` INT NOT NULL,
    PRIMARY KEY (`cat_id`),
    INDEX `temp_home_id_fk_idx` (`temp_home_id` ASC) VISIBLE,
    INDEX  `volunteer_id_fk_idx` (`volunteer_id` ASC) VISIBLE,
    CONSTRAINT `temp_home_id_fk`
        FOREIGN KEY (`temp_home_id`)
            REFERENCES `animalhelpcenter`.`temp_home` (`temp_home_id`)
            ON DELETE SET NULL
            ON UPDATE CASCADE;
CONSTRAINT `volunteer_id_fk`
        FOREIGN KEY (`volunteer_id`)
            REFERENCES `animalhelpcenter`.`volunteer` (`volunteer_id`)
            ON DELETE SET NULL
            ON UPDATE CASCADE;
);


UPDATE `animalhelpcenter`.`cat`
SET `status` = 'available'
where cat_id >= 1
  and cat_id < 27;
INSERT INTO `animalhelpcenter`.`cat` (`cat_id`, `cat_name`, `cat_age`, `cat_color`, `cat_sex`, `neutered`,
                                      `description`, `picture_path`, `status`, `cat_arrival`)
VALUES ('27', 'Muris', '4', 'Red', 'Male', 'Yes', 'Muris is quite independent but friendly cat', '/images/muris.jpg',
        'adopted', '2021-04-18');
INSERT INTO `animalhelpcenter`.`cat` (`cat_id`, `cat_name`, `cat_age`, `cat_color`, `cat_sex`, `neutered`,
                                      `description`, `picture_path`, `status`, `cat_arrival`)
VALUES ('28', 'Jack', '2', 'Grey', 'Male', 'No',
        'Jack was found on one of Riga streets. He is pretty cat, which needs some treatment before going home.',
        '/images/jack.png', 'sick', '2021-04-18');
INSERT INTO `animalhelpcenter`.`cat` (`cat_id`, `cat_name`, `cat_age`, `cat_color`, `cat_sex`, `neutered`,
                                      `description`, `picture_path`, `status`, `cat_arrival`)
VALUES ('29', 'Susie', '3', 'Grey', 'Female', 'No', 'Susie is beautiful young lady which loves people.',
        '/images/susie.jpg', 'adopted', '2021-04-18');
INSERT INTO `animalhelpcenter`.`cat` (`cat_id`, `cat_name`, `cat_age`, `cat_color`, `cat_sex`, `neutered`,
                                      `description`, `picture_path`, `status`, `cat_arrival`)
VALUES ('30', 'Linda', '6', 'Striped', 'Female', 'Yes',
        'Linda was once home cat. She was in bad condition when got in our center, but now she is almost recovered.',
        '/images/linda.jpg', 'sick', '2021-04-19');