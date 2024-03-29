# Security Flaws identified in whole application

## Analysis of Flaws
1. SQL Injection - the POST form data is **not** sanitised on any of the client and server, which means that the application is vulnerable to information disclosure and elevation of privilege threats
2. HTTP used over HTTPS - the sensitive POST form data (usernames, passwords, patient surnames) can be easily eavesdropped and interpreted (as it is decrypted and in plain text) if an attacker has either local or remote access to the network traffic logging mechanisms of a machine which runs the web application
3. Passwords are stored in plain text - the user passwords are stored in plain text in the SQL database, which means that they can easily be retrieved and interpreted through malicious use of the SQL database on a machine running the web app
4. Information leak in response headers - the server's response headers hold information about the version of the server framework which can then be used by attackers with access to the machines running the web application to find more Jetty exploits
5.	Password auth is implemented poorly – authentication brute force attacks are currently possible because weak passwords are allowed, and there is no tracking of failed login attempts or account lockouts
6.	Users can have the same username – the database schema does not enforce unique username entries
7.	SQL Server can be accessed without credentials – the sqlite3 database can be accessed both from code and in the terminal without any credentials, which means that bad actors with access to a machine running the web app can mutate or read the database

## Other to consider
1. HTTP Headers Requests (`GET`/`POST`) issues -
    - `GET`:
        * `cache-control`: must revalidate
        * `cache-control`: public
        * `Access-control-allow-origin`: should be `<origin>`
        https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin
        * `vary`(?)
    - `POST`:
        * `crossorigin="anonymous"`: same issue as c above.
        https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
2. Server runs in the same port everytime.
3. Text accepted should be in MD only (input validation).
4. Password:
    - Brute force attacks currently possible cause:
    - Needs minimum length and;
    - Weak passwords are allowed, needs combination of nums, symbols, ...
    - Not storing securily/hashing.
    - No tracking of failed attemps/account lockout.
5. Users can have the same username.
6. Surname can also be sql-injected.