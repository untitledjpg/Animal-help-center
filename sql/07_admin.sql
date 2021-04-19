CREATE TABLE `animalhelpcenter`.`admin` (
                                            `admin_id` INT NOT NULL AUTO_INCREMENT,
                                            `admin_login` VARCHAR(60) NOT NULL,
                                            `admin_password` VARCHAR(60) NOT NULL,
                                            PRIMARY KEY (`admin_id`));



INSERT INTO `animalhelpcenter`.`admin` (`admin_login`, `admin_password`) VALUES ('admin', 'admin123');


UPDATE animalhelpcenter.admin SET admin_password = md5('Test123') WHERE admin_id = 1;