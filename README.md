# Parking Application
Project for CIS 452 (Database Systems), Fall 2017.

## Description
The application assists in the management of an organization’s parking system. JavaFX is used to implement the application. An SQLite database is used. Connectivity to the database is handled using JDBC. There are three tabs that provide the core functionality of the program:

**People tab:** All people are listed along with their ID. New entries can be created using the menu on the right side.

**Vehicle tab:** Vehicles are listed with their VIN, plate, state, year, make, model, and color. New entries can be created using the menu on the right side.

**Parking pass tab:** The parking pass tab allows the user to link an existing person and an existing vehicle in the database to create a parking pass. A join query is used to display parking passes using the owner’s ID, owner’s first name, owner’s last name, vehicle’s vin, parking pass year, and parking pass type.


## Building
Gradle is used for build automation. The project can be built and run by using the command: 
```angular2html
./gradlew jfxRun
```
The resulting JAR is located in `PROJECT_ROOT/build/jfx/app/`

A native executable can be built by using the command:
```angular2html
./gradlew jfxNative
```