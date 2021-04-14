CREATE TABLE `cat` (
  `cat_id` int NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(70) NOT NULL,
  `cat_age` int NOT NULL,
  `cat_color` varchar(50) NOT NULL,
  `cat_sex` varchar(10) NOT NULL,
  `neutered` varchar(10) NOT NULL,
  `description` varchar(3000) NOT NULL,
  `picture_path` varchar(3000) NOT NULL,
  `status` varchar(100) DEFAULT NULL,
  `cat_arrival` varchar(45) DEFAULT NULL,
  `temp_home_id` int DEFAULT NULL,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

ALTER TABLE `animalhelpcenter`.`cat`
    ADD COLUMN `volunteer_id` INT NULL AFTER `temp_home_id`;





UPDATE `animalhelpcenter`.`cat` SET `status` = 'available' where cat_id >=1 and cat_id <27;
INSERT INTO `animalhelpcenter`.`cat` (`cat_id`, `cat_name`, `cat_age`, `cat_color`, `cat_sex`, `neutered`, `description`, `picture_path`, `status`, `cat_arrival`) VALUES ('27', 'Muris', '4', 'Red', 'Male', 'Yes', 'Muris is quite independent but friendly cat', '/images/muris.jpg', 'adopted', '2021-04-18');
INSERT INTO `animalhelpcenter`.`cat` (`cat_id`, `cat_name`, `cat_age`, `cat_color`, `cat_sex`, `neutered`, `description`, `picture_path`, `status`, `cat_arrival`) VALUES ('28', 'Jack', '2', 'Grey', 'Male', 'No', 'Jack was found on one of Riga streets. He is pretty cat, which needs some treatment before going home.', '/images/jack.png', 'sick', '2021-04-18');
INSERT INTO `animalhelpcenter`.`cat` (`cat_id`, `cat_name`, `cat_age`, `cat_color`, `cat_sex`, `neutered`, `description`, `picture_path`, `status`, `cat_arrival`) VALUES ('29', 'Susie', '3', 'Grey', 'Female', 'No', 'Susie is beautiful young lady which loves people.', '/images/susie.jpg', 'adopted', '2021-04-18');
INSERT INTO `animalhelpcenter`.`cat` (`cat_id`, `cat_name`, `cat_age`, `cat_color`, `cat_sex`, `neutered`, `description`, `picture_path`, `status`, `cat_arrival`) VALUES ('30', 'Linda', '6', 'Striped', 'Female', 'Yes', 'Linda was once home cat. She was in bad condition when got in our center, but now she is almost recovered.', '/images/linda.jpg', 'sick', '2021-04-19');