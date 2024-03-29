# Petfinder Demo App

------
> A humble demo of the Petfinder API used in a simple master-detail Android app.

### Build
- create an `api_key.properties` file in the `:auth` module, looking something like:
   ```
   client_id=<your_client_id>
   client_secret=<your_client_secret>
  ```  
- don't commit this file to version control
- run `./gradlew hideSecretFromPropertiesFile -PpropertiesFileName=./auth/api_key.properties 
  -Ppackage=com.riverpath.petfinderdemo.auth` to generate the `Secrets.kt` file. More details 
  can be found in the [plugin's github repo](https://github.com/klaxit/hidden-secrets-gradle-plugin)
- the rest should be the usual stuff

### Run the unit tests with
`gradle test`

### Feature Module Architecture
This project organizes its feature modules independently to avoid accidentally 
creating dependencies between modules and also reduce build times.

The `:features` module defines all the features supported by the application, by defining an 
interface for each feature. All feature modules depend on the `:features` module, implement 
the feature interface and inject an instance of the feature implementation in the `ServiceLocator`.

In order for the whole thing to work, we need to somehow include the feature modules in the 
application. That job is done by the `glue` module, which is an empty module that includes all 
feature modules.

```
             /----    [auth]   <---\
            /                       \
[features] <----    [petsearch]  <----  [glue] <--- [main]
   |         \                       / 
   |          \---- [petdetails] <--/
   ↓                / /
[common] <---------/ /
[pets]  <-----------/
```
Advantages of this approach:
 - keeps features as independent as possible
 - avoids accidental cross-feature dependencies
 - common logic on which multiple features are depending is defined in a separate module
 - requires a formal definition of a feature, by requiring all interaction with said feature to 
   be done through a well defined public interface