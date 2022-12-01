# SQL Injection Security Flaw

One of the identified vulnerabilities of the service is SQL Injection.

The form fields on the `login.html` page have no validation on the client-side and are sent as is to the server.
The server does not do ANY input validation either, which means that the form field values are used in the SQL statements as is.

This means that there are a few very serious threats that the service app is vulnerable to:
1. Tampering with data (dropping tables, inserting or editing entries)
2. Information disclosure (reading confidential patient data without needing the required access credentials)

[Put relevant vulnerability code snippets here from `AppServlet.java`]

[Further explanations, investigations]