

-- This sql is to be used together with DDL-Auto=update
-- It should be automatically executed on startup, but right now it is NOT working...

USE `ejc_chad_170_employee_database_dev`;


--
-- Data for table `department`
--
INSERT INTO `department` (id, name) VALUES (1,'Accountting');
INSERT INTO `department` (id, name) VALUES (2,'IT');
INSERT INTO `department` (id, name) VALUES (3,'Operation');
INSERT INTO `department` (id, name) VALUES (4,'Marketing');


--
-- Data for table `sport`
--
INSERT INTO `sport` (id, name) VALUES (1,'Football');
INSERT INTO `sport` (id, name) VALUES (2,'Basketball');
INSERT INTO `sport` (id, name) VALUES (3,'Swiming');
INSERT INTO `sport` (id, name) VALUES (4,'Running');


--
-- Data for table `spokenlanguage`
--
INSERT INTO `spokenlanguage` (id, name) VALUES (1,'English');
INSERT INTO `spokenlanguage` (id, name) VALUES (2,'Spanish');
INSERT INTO `spokenlanguage` (id, name) VALUES (3,'German');
INSERT INTO `spokenlanguage` (id, name) VALUES (4,'French');


--
-- Data for table `employee`
--
INSERT INTO `employee` (id, first_name, Last_name, email) VALUES (1,'Leslie','Andrews','leslie@luv2code.com');
INSERT INTO `employee` (id, first_name, Last_name, email) VALUES (2,'Emma','Baumgarten','emma@luv2code.com');
INSERT INTO `employee` (id, first_name, Last_name, email) VALUES (3,'Avani','Gupta','avani@luv2code.com');
INSERT INTO `employee` (id, first_name, Last_name, email) VALUES (4,'Yuri','Petrov','yuri@luv2code.com');
INSERT INTO `employee` (id, first_name, Last_name, email) VALUES (5,'Juan','Vega','juan@luv2code.com');
INSERT INTO `employee` (id, first_name, Last_name, email) VALUES (6,'Eduardo J.','Cabello','cabelloej@hotmail.com');

--
-- Data for table `relative`
--
INSERT INTO `relative` (id, employee_id, first_name, last_name, relation, gender,birth_date) VALUES (1,2,'Berta','Baumgarten','WIFE','Female','1988-06-13');
INSERT INTO `relative` (id, employee_id, first_name, last_name, relation, gender,birth_date) VALUES (2,2,'Jose','Baumgarten','SON','Male','1988-06-13');
INSERT INTO `relative` (id, employee_id, first_name, last_name, relation, gender,birth_date) VALUES (3,3,'Juan','Gupta','SON','Male','1988-06-13');
INSERT INTO `relative` (id, employee_id, first_name, last_name, relation, gender,birth_date) VALUES (4,3,'Maria','Gupta','WIFE','Female','1988-06-13');
INSERT INTO `relative` (id, employee_id, first_name, last_name, relation, gender,birth_date) VALUES (5,3,'Raul','Gupta','SON','Male','1988-06-13');
INSERT INTO `relative` (id, employee_id, first_name, last_name, relation, gender,birth_date) VALUES (6,6,'Eduardo Antonio','Cabello Martin','SON','Male','1988-06-13');
INSERT INTO `relative` (id, employee_id, first_name, last_name, relation, gender,birth_date) VALUES (7,6,'Daniel Armando','Cabello Martin','SON','Male','1988-06-13');
INSERT INTO `relative` (id, employee_id, first_name, last_name, relation, gender,birth_date) VALUES (8,6,'Falso','Cabello Martin','SON','Male','1988-06-13');


--
-- Data for table `employee_sport`
--
INSERT INTO `employee_sport` (employee_id, sport_id) VALUES (1, 1);
INSERT INTO `employee_sport` (employee_id, sport_id) VALUES (1, 3);
INSERT INTO `employee_sport` (employee_id, sport_id) VALUES (6, 1);
INSERT INTO `employee_sport` (employee_id, sport_id) VALUES (6, 2);



--
-- Data for table `employee_spokenlanguage`
--
INSERT INTO `employee_spokenlanguage` (employee_id, spokenlanguage_id, years_experience, proficiency) VALUES (1, 1, 5,2);
INSERT INTO `employee_spokenlanguage` (employee_id, spokenlanguage_id, years_experience, proficiency) VALUES (1, 3, 5,3);
INSERT INTO `employee_spokenlanguage` (employee_id, spokenlanguage_id, years_experience, proficiency) VALUES (6, 1, 5,2);
INSERT INTO `employee_spokenlanguage` (employee_id, spokenlanguage_id, years_experience, proficiency) VALUES (6, 2, 5,3);




