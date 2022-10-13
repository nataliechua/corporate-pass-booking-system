DROP SCHEMA IF EXISTS G1T5ddl;
CREATE SCHEMA G1T5ddl;
USE G1T5ddl;

SET GLOBAL local_infile = true;

CREATE TABLE STAFF
(
Staff_ID varchar(5) NOT NULL PRIMARY KEY,
Staff_Name varchar(50) NOT NULL,
Staff_Email varchar(50) NOT NULL,
Contact_Num varchar(20) NOT NULL,
Password varchar(50) NOT NULL,
Is_Admin_Hold varchar(5) NOT NULL,
Is_User_Active varchar(5) NOT NULL,
Staff_Type varchar(10) NOT NULL
);

CREATE TABLE PASS
(
Pass_ID varchar(4) NOT NULL PRIMARY KEY,
Pass_Type varchar(50) NOT NULL,
Attractions varchar(255) NOT NULL,
People_Per_Pass int NOT NULL,
Is_Digital varchar(5) NOT NULL,
Digital_Path varchar(50) NOT NULL,
Pass_Start_Date date NOT NULL,
Pass_Expiry_Date date NOT NULL,
Replacement_Fee float NOT NULL,
Is_Pass_Active varchar(5) NOT NULL
);

CREATE TABLE LOAN
(
Loan_ID varchar(6) NOT NULL PRIMARY KEY,
Staff_ID varchar(5) NOT NULL,
Loan_Date date NOT NULL,
Attraction varchar(50) NOT NULL,
Pass_ID varchar(255) NOT NULL,
Loan_Status varchar(20) NOT NULL,
CONSTRAINT loan_fk1 FOREIGN KEY (Staff_ID) REFERENCES STAFF(Staff_ID)
);

CREATE TABLE CONSTRAINTS
(
Loan_Per_Month int NOT NULL,
Pass_Per_Loan_Per_Day int NOT NULL,
Pass_Per_Month_Per_Staff_Per_Attraction int NOT NULL
);

/*Import Staff CSV File*/ 
LOAD DATA LOCAL INFILE 'C:/wamp64/www/oopProj/Database/staff.csv'
INTO TABLE STAFF   
FIELDS TERMINATED BY ','  
OPTIONALLY ENCLOSED BY '"'  
LINES TERMINATED BY '\n'   
IGNORE 1 ROWS;  

/*Import Pass CSV File*/ 
LOAD DATA LOCAL INFILE 'C:/wamp64/www/oopProj/Database/pass.csv'
INTO TABLE PASS 
FIELDS TERMINATED BY ','  
OPTIONALLY ENCLOSED BY '"'  
LINES TERMINATED BY '\n'   
IGNORE 1 ROWS;  

/*Import Loan CSV File*/ 
LOAD DATA LOCAL INFILE 'C:/wamp64/www/oopProj/Database/loan.csv'
INTO TABLE LOAN
FIELDS TERMINATED BY ','  
OPTIONALLY ENCLOSED BY '"'  
LINES TERMINATED BY '\n'   
IGNORE 1 ROWS;  

/*Import Constraints CSV File*/ 
LOAD DATA LOCAL INFILE 'C:/wamp64/www/oopProj/Database/constraints.csv'
INTO TABLE CONSTRAINTS   
FIELDS TERMINATED BY ','  
OPTIONALLY ENCLOSED BY '"'  
LINES TERMINATED BY '\n'   
IGNORE 1 ROWS;  
