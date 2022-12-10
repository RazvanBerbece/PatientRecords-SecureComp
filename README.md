# PatientRecords-SecureComp
Repository for CW2 of the COMP3911 - Secure Computing module in University of Leeds

# To Run
1. Make sure you have the `keystore.jks` file present in the root of the repo (this is so HTTPS can be used to access the web app)
2. Run `./gradlew run`
3. Navigate to `https://localhost:8080/` 

(**!! NOTE: THE HTTP PROTOCOL IS NO LONGER AVAILABLE FOR THE WEB APP, AND ONLY HTTPS IS AVAILABLE, SO MAKE SURE TO NAVIGATE TO HTTPS:// RATHER THAN HTTP:// !!**)

# Database
The database is an SQL database which is stored locally as a `.sqlite3` file. 
The database is then loaded at server run-time and used for queries.

## Schema
## Before fixes
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
### Example table record
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
## After fixes
Fixes were applied only to the `user` table and schema - added a new `INTEGER` column (`attempts`) and the passwords are now hashed with a static salt
```
CREATE TABLE user (id integer NOT NULL PRIMARY KEY, 
name varchar (30) NOT NULL, 
username varchar (12) NOT NULL UNIQUE, 
password char (64) NOT NULL, 
attempts INTEGER DEFAULT (0) NOT NULL);
```
### Example table record
Table `user` (`select * from user`)
```
id|name|username|password|attempts
1|Nick Efford|nde|7d03f25f65c9b887e9f05efe510e9b4cce8ee8255c3d0c9c01617f96d7692c33|0
2|Mary Jones|mjones|20d6602275105255732b0b27a6ddd8a751f246ada69d5a03ee48af64ca735f04|0
3|Andrew Smith|aps|e6b3679a4006dd1fc275cf75c25c2b670f3aec3f111cb6b6ec7dcf000963bff8|0
```