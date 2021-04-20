CREATE DEFINER=`root`@`localhost` PROCEDURE `addAdoptApplication`(
in id int,
in name nvarchar(60),
in surname nvarchar(60),
in phone_number int,
in email nvarchar(60),
in catId int,
in other_pets nvarchar(60),
in children nvarchar(60),
out new_id int)
BEGIN
IF id=0 THEN
   insert into adoption_app (name, surname, phone_number, email, cat_id, other_pets, children)
   values(name, surname, phone_number, email, catId, other_pets, children);
SELECT LAST_INSERT_ID() into new_id;
ELSE
update adoption_app set name = name, surname = surname,
                        phone_number = phone_number, email = email, cat_id = cat_id, other_pets = other_pets,
                        children = children
where id = id;

select id into new_id;
END IF;
END