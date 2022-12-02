# SQL Injection Security Flaw

One of the identified vulnerabilities of the service is SQL Injection.

The form fields on the `login.html` page have no validation on the client-side and are sent as is to the server.
The server does not do ANY input validation either, which means that the form field values are used in the SQL statements as is.

This means that there are a few very serious threats that the service app is vulnerable to:
1. Tampering with data (dropping tables, inserting or editing entries)
2. Information disclosure (reading confidential patient data without needing the required access credentials)
3. Elevation of Privilege (being able to search for patient details without being a registered user of the system)

# To Reproduce
1. Input an existing username from the DB in the username field 
1. Input the `' or '1'='1` string into the password field
2. Submit

The application will display `No records found.`, which means that the server ran the query with an empty surname string for an unauthorized user (no password provided)

**Note**: The username field is vulnerable to SQL injection too, which means that the login portal can be attacked without knowing an existing username either.

[Put relevant vulnerability code snippets here from `AppServlet.java`]

[Further explanations, investigations]