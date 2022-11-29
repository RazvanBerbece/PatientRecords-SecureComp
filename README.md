# PatientRecords-SecureComp
Repository for CW2 of the COMP3911 - Secure Computing module in University of Leeds

# Database
The database is an SQL database which is stored locally as a `.sqlite3` file. The database is then loaded at server run-time nad used for queries.

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