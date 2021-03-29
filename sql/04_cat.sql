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