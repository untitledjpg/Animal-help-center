CREATE TABLE `animalhelpcenter`.`adoption_app` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `name` VARCHAR(45) NOT NULL,
                                                   `surname` VARCHAR(45) NOT NULL,
                                                   `phone_number` INT NOT NULL,
                                                   `email` VARCHAR(45) NOT NULL,
                                                   `catId` INT NOT NULL,
                                                   `other_pets` VARCHAR(45) NOT NULL,
                                                   `children` VARCHAR(45) NOT NULL,
                                                   PRIMARY KEY (`id`));
