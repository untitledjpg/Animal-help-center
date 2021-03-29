CREATE TABLE `volunteer` (
  `volunteer_id` int NOT NULL AUTO_INCREMENT,
  `volunteer_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `volunteer_surname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `volunteer_phone` int NOT NULL,
  `volunteer_email` varchar(300) NOT NULL,
  PRIMARY KEY (`volunteer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci