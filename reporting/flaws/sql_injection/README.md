# SQL Injection Security Flaw

The first identified vulnerability of the service is SQL Injection.

The form fields on the `login.html` page have no validation on the client-side and are sent as is to the server.
The server does not do ANY input validation either, which means that the form field values are used in the SQL statements as is.

<Put code snippets here from>

<Further explanations, investigations>