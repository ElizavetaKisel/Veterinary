# Veterinary Clinic

## RESTful Web-application is written using Java 11, Spring, Gradle, Hibernate, MySQL.
This application is used to register clients and their pets in the veterinary clinic's database, so as keeping a record of their appointments.

### RESTful API Description
**1. for User**

METHOD | PATH | DESCRIPTION
------------|-----|------------
GET | /api/users | get all users
GET | /api/users/params | get user's info by params (without params - get info of all users)
GET | /api/users/{id}/info | get user's info by id
GET | /api/users/{id} | get user by id
GET | /api/users/{id}/pets | get user's pets by id
POST | /api/users | add new user
DELETE | /api/users/{id} | delete user by id
PATCH | /api/users/{id} | update user's info by id
PATCH | /api/users/{id} | update user's role by id (for admin)

**2. for Pet**

METHOD | PATH | DESCRIPTION
------------|-----|------------
GET | /api/pets| get pets by params
GET | /api/pets/{id} | get pet by id
GET | /api/pets/{id}/owners | get pet's owners by id
POST | /api/pets | add new pet
DELETE | /api/pets/{id} | delete pet by id
PATCH | /api/pets/{id} | update pet by id

**3. for Appointments**

METHOD | PATH | DESCRIPTION
------------|-----|------------
GET | /api/appointments | get appointments by params
GET | /api/appointments/{id} | get appointment by id
POST | /api/appointments | add new appointment (for admin)
DELETE | /api/appointments/{id} | delete appointment by id (for admin)
PATCH | /api/appointments/{id} | update appointment by id (for admin)
PATCH | /api/appointments/{id}/make | make an appointment by id (for customers)
PUT | /api/appointments/{id} | close appointment by id (for doctors)
PUT | /api/appointments/{id}/return | return appointment by id (for customers)

### Try in Postman ↓↓↓
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/18923979-ec719a86-7a58-4acb-b199-0677322ac50a?action=collection%2Ffork&collection-url=entityId%3D18923979-ec719a86-7a58-4acb-b199-0677322ac50a%26entityType%3Dcollection%26workspaceId%3D5f338a47-d2e8-45a1-bbeb-7e3eeaba9b77)
