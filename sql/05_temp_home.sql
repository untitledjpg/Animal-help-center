CREATE TABLE `temp_home` (
  `temp_home_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `phone` int NOT NULL,
  `email` varchar(300) NOT NULL,
  `address` varchar(3000) NOT NULL,
  PRIMARY KEY (`temp_home_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci