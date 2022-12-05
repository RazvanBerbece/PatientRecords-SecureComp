# Security Flaws Observations and Possible Vulnerabilities

1. No use of SQL prepared statements.
2. Server runs in the same port everytime.
3. Text accepted should be in MD only (input validation).
4. Password:
    - Brute force attacks currently possible cause:
    - Needs minimum length and;
    - Weak passwords are allowed, needs combination of nums, symbols, ...
    - Not storing securily/hashing.
    - No tracking of failed attemps/account lockout.
5. HTTP Headers Requests (`GET`/`POST`):
    - `GET`:
        * `cache-control`: must revalidate
        * `cache-control`: public
        * `Access-control-allow-origin`: should be `<origin>`
        https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin
        * `vary`(?)
    - `POST`:
        * `crossorigin="anonymous"`: same issue as c above.
        https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
6. Users can have the same username.
7. Surname can also be sql-injected.
