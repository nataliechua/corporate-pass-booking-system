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
Saturday_Borrower bigint,
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
LOAD DATA INFILE 'C:/wamp64/tmp/oop_database/staff.csv'
INTO TABLE STAFF   
FIELDS TERMINATED BY ','  
OPTIONALLY ENCLOSED BY '"'  
LINES TERMINATED BY '\r\n'   
IGNORE 1 ROWS;  

/*Import Pass CSV File*/ 
LOAD DATA INFILE 'C:/wamp64/tmp/oop_database/pass.csv'
INTO TABLE PASS 
FIELDS TERMINATED BY ','  
OPTIONALLY ENCLOSED BY '"'  
LINES TERMINATED BY '\r\n'   
IGNORE 1 ROWS;  

/*Import Loan CSV File*/ 
LOAD DATA INFILE 'C:/wamp64/tmp/oop_database/loan.csv'
INTO TABLE LOAN
FIELDS TERMINATED BY ','  
OPTIONALLY ENCLOSED BY '"'  
LINES TERMINATED BY '\r\n'   
IGNORE 1 ROWS;  

/*Import Constraints CSV File*/ 
LOAD DATA INFILE 'C:/wamp64/tmp/oop_database/constraint.csv'
INTO TABLE CONSTRAINTS   
FIELDS TERMINATED BY ','  
OPTIONALLY ENCLOSED BY '"'  
LINES TERMINATED BY '\n'   
IGNORE 1 ROWS;

-- select p.pass_id, p.pass_type from pass p 
-- inner join loan on loan.pass_id=p.pass_id
-- where loan.loan_date = "2022-10-07";

INSERT INTO loan_pass
VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 1),
(7, 1),
(8, 7),
(8, 8);

select * from loan_pass;

select * from loan l;

select * from pass ;

select * from staff;

select * from constraints;

-- select * from loan l inner join pass p on l.pass_id = p.pass_id;

-- ect pass.pass_id, pass.pass_type, pass.attractions, pass.people_per_pass, pass.is_digital, pass.digital_path, pass.pass_start_date, pass.pass_expiry_date, pass.replacement_fee, pass.is_Pass_Active from pass