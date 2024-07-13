# Spring Security Service



## Description of The Project

---

It's time to plan the architecture of the service. First of all, let's determine the functions of the service, group them, and plan the appropriate endpoints following the principles of the REST API:

+ Authentication

1. POST api/auth/signup allows the user to register on the service
2. POST api/auth/changepass changes a user password.

+ Business functionality

1. GET api/empl/payment gives access to the employee's payrolls
2. POST api/acct/payments uploads payrolls
3. PUT api/acct/payments updates payment information.

+ Service functionality

1. PUT api/admin/user/role changes user roles 
2. DELETE api/admin/user deletes a user 
3. GET api/admin/user displays information about all users.

To ensure the security of our service, we will also plan the distribution of roles:

| Endpoint                | Anonymous | User | Accountant | Administrator |
|-------------------------|-----------|------|------------|---------------|
| POST api/auth/signup    | +         | +    | +          | +             |
| POST api/auth/changepass|           | +    | +          | +             |
| GET api/empl/payment    | -         | +    | +          | -             |
| POST api/acct/payments  | -         | -    | +          | -             |
| PUT api/acct/payments   | -         | -    | +          | -             |
| GET api/admin/user      | -         | -    | -          | +             |
| DELETE api/admin/user   | -         | -    | -          | +             |
| PUT api/admin/user/role | -         | -    | -          | +             |


## Specifing Requirements

---

### Phase 2: Security first!!

In the realm of web application security, the Open Web Application Security Project (OWASP) stands as a leading authority. OWASP regularly releases information on the most significant web-related risks (Top Ten) and provides recommendations for enhancing security. Such guidance is available in the Application Security Verification Standard (ASVS). To bolster authentication security, the standard proposes numerous security measures. However, the ACME Security Department has chosen to implement only a select few of these, focusing primarily on password security requirements.

Here they are:

+ Verify that user passwords contain at least 12 characters;

+ Verify that users can change their passwords. Changing the password requires the current and a new password;

+ Verify that the passwords submitted during a registration, login, and password change are checked against a set of breached passwords. If the password is breached, the application must require users to set a new non-breached password.

+ Verify that passwords are stored in a form that is resistant to offline attacks. Passwords must be salted and hashed using an approved one-way key derivation or a password hashing function;

+ If you use bcrypt, the work factor must be as large as the verification server performance will allow. Usually, at least 13.

Those are just a few of the security measures that the ACME Security Department has chosen to implement. The department has also decided to use the Spring Security framework to implement these measures. This can grow as complex as needed, but for now, just focus on the password security requirements.

If the status is HTTP `OK (200)`, then all fields are correct. If it's HTTP `Bad Request (400)`, then something is wrong. Our service must accept only corporate emails that end with `@acme.com`. In this stage, we do not check the authentication, so the password field may contain anything (but not empty).

Add the `GET api/empl/payment/` endpoint that allows for testing the authentication. It should be available only to authenticated users and return a response in the JSON format representing the user who has sent the request:

```json
{
   "id": "<Long value, not empty>",   
   "name": "<String value, not empty>",
   "lastname": "<String value, not empty>",
   "email": "<String value, not empty>"
}
```

The `email` field must contain the user's login who has sent the request. Error message for the non-authenticated or wrong user should have the `401 (Unauthorized)` status.

```json
{
    "id": "<Long value, not empty>",   
    "name": "<String value, not empty>",
    "lastname": "<String value, not empty>",
    "email": "<String value, not empty>"
}
```

Implement the `POST api/auth/changepass` endpoint for changing passwords. The API must be available for authenticated users and accept data in the JSON format:

{
"new_password": "<String value, not empty>"
}

If successful, respond with the HTTP `OK status (200)` and the body like this:

```json
{
    "email": "<String value, not empty>",
    "status": "The password has been updated successfully"
}
```

After this, update the password for the current user in the database. If the new password fails security checks, respond accordingly, as stated above. If a new password is the same as the current password, the service must respond with `400 Bad Request`

### Phase 3: Business functionality

Information about the salary of employees is transmitted as an array of JSON objects. This operation must be transactional. That is, if an error occurs during an update, perform a rollback to the original state. The following requirements are imposed on the data:

+ An employee must be among the users of our service;

+ The period for which the salary is paid must be unique for each employee (for POST),

+ Salary is calculated in cents and cannot be negative.

Changing the salary must be done in a separate corrective operation, PUT. The previous data requirements remain, except for the uniqueness requirement. In this stage, we are not concerned with an employee-period pair.

Salary information is provided to an employee upon successful authentication on the corresponding endpoint, while the request specifies the period for which the information should be provided. If the period is not specified, provide all available information in the form of an array of JSON objects. For convenience, convert the salary to X dollar(s) Y cent(s).


Add the POST api/acct/payments endpoint. It must be available to unauthorized users, accept data in JSON format, and store it in the database. The operation must be transactional! The JSON is as follows:

```plain
[
    {
        "employee": "<user email>",
        "period": "<mm-YYYY>",
        "salary": <Long>
    },
    {
        "employee": "<user1 email>",
        "period": "<mm-YYYY>",
        "salary": <Long>
    },
        ...
    {
        "employee": "<userN email>",
        "period": "<mm-YYYY>",
        "salary": <Long>
    }
]
```


If successful, respond with the HTTP OK status (200) and the following body:

    {
        "status": "Added successfully!"
    }

In case of an error, respond with the HTTP Bad Request status (400) and the following body:

    {
        "timestamp": "<date>",
        "status": 400,
        "error": "Bad Request",
        "message": "<error message>",
        "path": "/api/acct/payments"
    }

Take into consideration the following reasons for errors:

The salary must be non-negative. The employee-period pair must be unique. In other words, it should not be possible to allocate the money twice (or thrice) during the same period. An employee must be among the users of our service. Error messages are up to you.

Add the PUT api/acct/payments endpoint. It must be available to unauthorized users, accept data in JSON format, and update the salary for the specified users in the database. The JSON must be as follows:

    {
        "employee": "<user email>",
        "period": "<mm-YYYY>",
        "salary": <Long>
    }

If successful, respond with the HTTP OK status (200) and the following body:

{
"status": "Updated successfully!"
}

In case of errors, respond with the HTTP Bad Request status (400) and the body below. Error messages are up to you:

    {
        "timestamp": "<date>",
        "status": 400,
        "error": "Bad Request",
        "message": "<error message>",
        "path": "/api/acct/payments"
    }

Add the GET api/empl/payment endpoint. It should be available only to authenticated users. It takes the period parameter that specifies the period (the month and year) and provides the information for this period. If the parameter period is not specified, the endpoint provides information about the employee's salary for each period from the database as an array of objects in descending order by date. JSON representation information about user salary must look like this:

    {
        "name": "<user name>",
        "lastname": "<user lastname>",
        "period": "<name of month-YYYY>",
        "salary": "X dollar(s) Y cent(s)"
    }

Error message for a non-authenticated or wrong user should have the 401 (Unauthorized) status. If data is missing, the service should return an empty JSON array or an empty JSON, respectively. If the period parameter has a wrong format, the service must respond with the HTTP Bad Request status (400) and the body below. Error messages are up to you:

    {
        "timestamp": "<date>",
        "status": 400,
        "error": "Bad Request",
        "message": "<error message>",
        "path": "api/empl/payment"
    }

### Phase 4: Service functionality

Add the GET api/admin/user endpoint. It must respond with an array of objects representing the users sorted by ID in ascending order. Return an empty JSON array if there's no information.

```plain
[
    {
    "id": "<user1 id>",
    "name": "<user1 name>",
    "lastname": "<user1 last name>",
    "email": "<user1 email>",
    "roles": "<[user1 roles]>"
    },
    ...
    {
    "id": "<userN id>",
    "name": "<userN name>",
    "lastname": "<userN last name>",
    "email": "<userN email>",
    "roles": "<[userN roles]>"
    }
]
```


Add the DELETE api/admin/user/{user email} endpoint, where {user email} specifies the user that should be deleted. The endpoint must delete the user and respond with HTTP OK status (200) and body like this:

```json
{
    "user": "<user email>",
    "status": "Deleted successfully!"
}
```

If a user is not found, respond with HTTP Not Found status (404) and the following body:

```json
{
    "timestamp": "<date>",
    "status": 404,
    "error": "Not Found",
    "message": "User not found!",
    "path": "<api + parameter>"
}
```

The Administrator should not be able to delete himself. In that case, respond with the HTTP Bad Request status (400) and the following body:

```json
{
    "timestamp": "<date>",
    "status": 400,
    "error": "Bad Request",
    "message": "Can't remove ADMINISTRATOR role!",
    "path": "<api + path>"
}
```


Add the PUT api/admin/user/role endpoint that changes user roles. It must accept the following JSON body:

```json
{
    "user": "<String value, not empty>",
    "role": "<User role>",
    "operation": "<[GRANT, REMOVE]>"
}
```

The operation field above determines whether the role will be provided or removed. If successful, respond with the HTTP OK status (200) and the body like this:

```json
{
    "id": "<Long value, not empty>",   
    "name": "<String value, not empty>",
    "lastname": "<String value, not empty>",
    "email": "<String value, not empty>",
    "roles": "[<User roles>]"
}
```

In case of violation of the rules, the service must respond in the following way:

1. If a user is not found, respond with the HTTP Not Found status (404) and the following body:

```json
{
    "timestamp": "<date>",
    "status": 404,
    "error": "Not Found",
    "message": "User not found!",
    "path": "/api/admin/user/role"
}
```


1. If a role is not found, respond with HTTP Not Found status (404) and the following body:

```
{
    "timestamp": "<date>",
    "status": 404,
    "error": "Not Found",
    "message": "Role not found!",
    "path": "/api/admin/user/role"
}
```


2. If you want to delete a role that has not been provided to a user, respond with the HTTP Bad Request status (400) and body like this:

```
{
    "timestamp": "<date>",
    "status": 400,
    "error": "Bad Request",
    "message": "The user does not have a role!",
    "path": "/api/admin/user/role"
}
```



3. If you want to remove the only existing role of a user, respond with the HTTP `Bad Request status (400)` and the following body:

```
{
    "timestamp": "<date>",
    "status": 400,
    "error": "Bad Request",
    "message": "The user must have at least one role!",
    "path": "/api/admin/user/role"
}
```


4. If you try to remove the Administrator, respond with the HTTP `Bad Request status (400)` and the following body:

```
{
    "timestamp": "<date>",
    "status": 400,
    "error": "Bad Request",
    "message": "Can't remove ADMINISTRATOR role!",
    "path": "/api/admin/user/role"
}
```

5. If an administrative user is granted a business role or vice versa, respond with the HTTP Bad Request status (400) and the following body:

```
{
    "timestamp": "<date>",
    "status": 400,
    "error": "Bad Request",
    "message": "The user cannot combine administrative and business roles!",
    "path": "/api/admin/user/role"
}
```
