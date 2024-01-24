# Petfinder Demo App

------
> A humble demo of the Petfinder API used in a simple master-detail Android app.

### Run the unit tests with
`gradle test`

### Module Architecture
Have a top module defining essential entities and API contracts. All modules depend on it. 
Feature modules also depend on :main, which provides the service locator infrastructure. 
Alternatively, we have a :hub module which handles the service locator, and :main depends on it. 