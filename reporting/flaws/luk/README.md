# Security Flaws Observations and Possible Vulnerabilities

1. No use of SQL prepared statements.
2. Server runs in the same port everytime.
3. Text accepted should be in MD only (input validation).
4. Password:
    a. Needs minimum length and;
    b. Weak passwords are allowed, needs combination of nums, symbols, ...
    c. Brute force attacks currently possible cause:
    d. Not storing securily/hashing.
    e. No tracking of failed attemps/account lockout.
5. Requests (`GET`/`POST`):
    `GET`:
        a. `cache-control`: must revalidate
        b. `cahce-control`: public
        c. `Access-control-allow-origin`: should be `<origin>`
        d. `vary`(?)
    `POST`:
        a. `crossorigin="anonymous"`: same issue as c above.

