# OOPG1T5
## Installation guide
1. Download this group-project-g1t5 folder to 'C:\wamp64\www'
2. Copy and paste the oop_database folder into 'C:\wamp64\tmp'
3. Set up and run a WAMP or MAMP server
4. Execute the contents of 'script.sql' (/oop_database) in phpMyAdmin, i.e. at:
    http://localhost/phpmyadmin OR http://localhost/phpMyAdmin
5. use Visual Studio Code (or any other IDE) open this OOP folder
6. go to 'OOP\demo\src\main\java\project', right click on file 'Is424G1T5Application' and click 'Run Java'
7. Go to http://localhost:8080 where the application should be working!

## API Endpoints
### Staff 
- Get all staff: GET http://localhost:8080/staff
- Get staff by ID: http://localhost:8080/staff/id
- Create new staff: POST http://localhost:8080/staff
- Update staff by ID: http://localhost:8080/staff/id
### Pass
- Get all passes: GET http://localhost:8080/passes
- Get pass by ID: GET http://localhost:8080/passes/id
- Create new pass: POST http://localhost:8080/passes
- Update pass by ID: PUT http://localhost:8080/passes/id
### Loan
- Get all loans: GET http://localhost:8080/loans
- Get loans by ID: GET http://localhost:8080/loans/id
### Constraint
- Get all constraints: GET http://localhost:8080/constraint
- Get constraint by ID: GET http://localhost:8080/constraint/id
- Update constraint: PUT http://localhost:8080/constraint
- Update constraint data by ID: PUT http://localhost:8080/constraint/id
### Booker
- Update constraint data by ID: PUT http://localhost:8080/constraint/id

## User Account
### Admin
- Eva.Yong@sportsschool.edu.sg; 123456
- Sophia.Toh@sportsschool.edu.sg; 123456
### Staff
- Liam.The@sportsschool.edu.sg; 123456
- Noah.Ng@sportsschool.edu.sg; 123456
- Oliver.Tan@sportsschool.edu.sg; 123456
- William.Fu@sportsschool.edu.sg; 123456
- James.Tong@sportsschool.edu.sg; 123456
- Eric.Loh@sportsschool.edu.sg; 123456
### GOP
- Noah.Goh@sportsschool.edu.sg; 123456

## Live Reload
- Install this extension: https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei