# PatientRecords-SecureComp
Repository for CW2 of the COMP3911 - Secure Computing module in University of Leeds

# To Run
1. Make sure you have the `keystore.jks` file present in the root of the repo (this is so HTTPS can be used to access the web app)
2. Run `./gradlew run`
3. Navigate to `https://localhost:8080/` 
(**NOTE: THE HTTP PROTOCOL IS NO LONGER AVAILABLE FOR THE WEB APP, AND ONLY HTTPS IS AVAILABLE, SO MAKE SURE TO NAVIGATE TO HTTPS:// RATHER THAN HTTP://**)

# Database
The database is an SQL database which is stored locally as a `.sqlite3` file. 
The database is then loaded at server run-time and used for queries.

## Schema
```
CREATE TABLE user (id integer not null primary key, 
name varchar(30) not null, 
username varchar(12) not null, 
password char(8) not null );

CREATE TABLE patient (id integer not null primary key, 
surname varchar(30) not null, 
forename varchar(30) not null, 
address varchar(100) not null, 
born date not null, 
gp_id integer, 
treated_for varchar(50) );
```

## Example table record
Table `user` (`select * from user`)
```
id|name|username|password
1| Nick Efford  |nde    |wysiwyg0
2| Mary Jones   |mjones |marymary
3| Andrew Smith |aps    |abcd1234
```

Table `patient` (`select * from patient`)
```
surname|forename|address|born|dp_id|treated_for
1|Davison|Peter|27 Rowan Avenue, Hightown, NT2 1AQ|1942-04-12|4|Lung cancer
2|Baird|Joan|52 The Willows, Lowtown, LT5 7RA|1927-05-08|17|Osteoarthritis
3|Stevens|Susan|36 Queen Street, Histon, HT3 5EM|1989-04-01|2|Asthma
4|Johnson|Michael|The Barn, Yuleville, YV67 2WR|1951-11-27|10|Liver cancer
5|Scott|Ian|4 Vale Rise, Bingham, BG3 8GD|1978-09-15|15|Pneumonia
```