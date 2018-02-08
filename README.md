### AppForm Service: 

Service to submit forms and view forms. An exercise in Spring and Angular 4 in reality. 

#### How to use:

Run `make` to do both build and deploy locally.

Run `make build` to build.

Run `make deploy` to deploy locally.

To access the service, visit `localhost:8080`.

#### API and Database: 

REST API can be consumed from `localhost:8080/api/*`

Endpoints are following: 
1. `GET /api/sectors` - returns sector tree.
2. `GET /api/form-submissions` - returns empty form. 
3. `GET /api/form-submissions` - returns saved (or empty if user has no stored forms) submitted forms. 
4. `POST /api/form-submissions` - saves given form. 

Database dump is stored in [src/main/resources/data.sql](src/main/resources/data.sql).
It is automatically loaded on each application launch. 