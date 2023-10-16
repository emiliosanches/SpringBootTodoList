# Spring Boot Todo List Endpoints

Below, there is a list of the endpoints available for using this application.

## Users

### POST /users
Create a new user with: name, username and password. Returns the created user.

#### Request body
```json
{
  "name": "John Doe",
  "username": "john_doe",
  "password": "dummyP4$$word"
}
```

#### Success response body
```json
{
	"id": "e58feccf-2f7f-44d7-a556-ca293b5f17ba",
	"username": "john_doe",
	"name": "John Doe",
	"password": "$2a$12$807tvA09INC1IXEjYvHjEuJLY1Ty5UiHWtnyBtDqmPGpsJlWQqy0m",
	"createdAt": "2023-10-16T21:26:08.117907008"
}
```

## Tasks

### POST /tasks (basic auth)
Creates a new task owned by the user. Uses basic authentication (required).
Basic authentication is done by sending (in every authenticated request) the base64 representation of the concatenation of the username, a colon and the password, prefixed by "Basic ", i.e.
```js
const token = base64Encoder(username + ":" + password);

const headers = {
  Authorization: `Basic ${token}`
}
```

#### Request headers
```
POST /tasks HTTP/2
Host: springboot-todolist.onrender.com
content-type: application/json
content-length: 160
authorization: Basic am9obl9kb2U6ZHVtbXlQNCQkd29yZA==   # This is header that authenticates the user 
```

#### Request body
```json
{
	"description": "Some good description",
	"title": "Do something",
	"startsAt": "2023-10-20T10:00:00",
	"endsAt": "2023-10-21T12:00:00",
	"priority": "HIGH"
}
```

#### Success Response body
```json
{
	"id": "4862a083-3abb-4237-a268-1b0fd53f8b16",
	"description": "Some good description",
	"title": "Do something",
	"startsAt": "2023-10-20T10:00:00",
	"endsAt": "2023-10-21T12:00:00",
	"priority": "HIGH",
	"createdAt": "2023-10-16T21:31:47.608278598",
	"userId": "3a58c5e5-ee48-43ed-8d29-b661f6cc7e4a"
}
```

### GET /tasks (basic auth)
Lists all the tasks owned by the user. Uses basic authentication (required).
See [POST /tasks authentication method](#post-tasks-basic-auth) to learn how basic auth works.

#### Request headers
```
GET /tasks HTTP/2
Host: springboot-todolist.onrender.com
authorization: Basic am9obl9kb2U6ZHVtbXlQNCQkd29yZA==   # This is header that authenticates the user 
```

#### Request body
> GET request requires no body

#### Success Response body
```json
[
	{
		"id": "4862a083-3abb-4237-a268-1b0fd53f8b16",
		"description": "Some good description",
		"title": "Do something",
		"startsAt": "2023-10-20T10:00:00",
		"endsAt": "2023-10-21T12:00:00",
		"priority": "HIGH",
		"createdAt": "2023-10-16T21:31:47.608279",
		"userId": "3a58c5e5-ee48-43ed-8d29-b661f6cc7e4a"
	}
]
```

### PUT /tasks/{id} (basic auth)
Updates a task owned by the user, identified by the {id} parameter. Uses basic authentication (required).
See [POST /tasks authentication method](#post-tasks-basic-auth) to learn how basic auth works.

#### Request headers
```
PUT /tasks/4862a083-3abb-4237-a268-1b0fd53f8b16 HTTP/2
Host: springboot-todolist.onrender.com
content-type: application/json
content-length: 50
authorization: Basic am9obl9kb2U6ZHVtbXlQNCQkd29yZA==   # This is header that authenticates the user 
```

#### Request body
```json
{
	"description": "This description was updated"
}
```

#### Success Response body
```json
{
	"id": "4862a083-3abb-4237-a268-1b0fd53f8b16",
	"description": "This description was updated",
	"title": "Do something",
	"startsAt": "2023-10-20T10:00:00",
	"endsAt": "2023-10-21T12:00:00",
	"priority": "HIGH",
	"createdAt": "2023-10-16T21:31:47.608279",
	"userId": "3a58c5e5-ee48-43ed-8d29-b661f6cc7e4a"
}
```