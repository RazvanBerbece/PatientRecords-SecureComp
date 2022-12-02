# Security Flaws identified in whole application

## Analysis of Flaws
1. SQL Injection - the POST form data is **NOT** sanitised on none of the client and server, which means that the application is vulnerable to information disclosure and elevation of privilege threats
2. HTTP used over HTTPS - the sensitive POST form data (usernames, passwords, patient surnames) can be easily eavesdropped and interpreted (as it is decrypted and in plain text) if an attacker has either local or remote access to the network traffic logging mechanisms of a machine which runs the web application
3. Passwords are stored in plain text - the user passwords are stored in plain text in the SQL database, which means that they can easily be retrieved and interpreted through malicious use of the SQL database on a machine running the web app