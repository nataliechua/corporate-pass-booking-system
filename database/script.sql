DROP SCHEMA IF EXISTS G1T5ddl;
CREATE SCHEMA G1T5ddl;
USE G1T5ddl;

SET GLOBAL local_infile = true;

CREATE TABLE STAFF
(
Staff_ID bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
Staff_Name varchar(50) NOT NULL,
Staff_Email varchar(50) NOT NULL,
Contact_Num varchar(20) NOT NULL,
Password varchar(100) NOT NULL,
Is_Admin_Hold varchar(5) NOT NULL,
Is_User_Active varchar(5) NOT NULL,
Staff_Type varchar(10) NOT NULL
);

CREATE TABLE PASS
(
Pass_ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
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
Loan_ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
Staff_ID bigint NOT NULL,
Loan_Date date NOT NULL,
Attraction varchar(50) NOT NULL,
Loan_Status varchar(20) NOT NULL,
CONSTRAINT loan_fk1 FOREIGN KEY (Staff_ID) REFERENCES STAFF(Staff_ID)
);

CREATE TABLE LOAN_PASS
(
Loan_ID BIGINT NOT NULL,
Pass_ID bigint NOT NULL,
CONSTRAINT loanpass_pk PRIMARY KEY (Loan_ID, Pass_ID),
CONSTRAINT loanpass_fk1 FOREIGN KEY (Loan_ID) REFERENCES LOAN(Loan_ID),
CONSTRAINT loanpass_fk2 FOREIGN KEY (Pass_ID) REFERENCES PASS(Pass_ID)
);

CREATE TABLE CONSTRAINTS
(
Constraint_ID bigint not null,
Constraint_Name varchar(255) not null,
Constraint_Data int not null
);

/*Import Staff CSV File*/ 
LOAD DATA LOCAL INFILE 'C:/wamp64/www/oopProj/group-project-g1t5/database/staff.csv'
INTO TABLE STAFF   
FIELDS TERMINATED BY ','  
OPTIONALLY ENCLOSED BY '"'  
LINES TERMINATED BY '\r\n'   
IGNORE 1 ROWS;  

/*Import Pass CSV File*/ 
LOAD DATA LOCAL INFILE 'C:/wamp64/www/oopProj/group-project-g1t5/database/pass.csv'
INTO TABLE PASS 
FIELDS TERMINATED BY ','  
OPTIONALLY ENCLOSED BY '"'  
LINES TERMINATED BY '\n'   
IGNORE 1 ROWS;  

/*Import Loan CSV File*/ 
LOAD DATA LOCAL INFILE 'C:/wamp64/www/oopProj/group-project-g1t5/database/loan.csv'
INTO TABLE LOAN
FIELDS TERMINATED BY ','  
OPTIONALLY ENCLOSED BY '"'  
LINES TERMINATED BY '\n'   
IGNORE 1 ROWS;  

/*Import Constraints CSV File*/ 
LOAD DATA LOCAL INFILE 'C:/wamp64/www/oopProj/group-project-g1t5/database/constraint.csv'
INTO TABLE CONSTRAINTS   
FIELDS TERMINATED BY ','  
OPTIONALLY ENCLOSED BY '"'  
LINES TERMINATED BY '\n'   
IGNORE 1 ROWS;

INSERT INTO loan_pass
VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);