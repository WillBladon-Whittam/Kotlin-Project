# COM534 Full Kotlin Project - Will Bladon-Whittam

## Setup

### Environment

- Kotlin:       1.9.22
- Groovy:       3.0.17
- Ant:          Apache Ant(TM) version 1.10.13
- JVM:          21.0.5 (Amazon.com Inc. 21.0.5+11-LTS)
- OS:           Windows 10 10.0 amd64

### Running instructions

To run ensure the sources directory is set to "src":
- Open in IntelliJ IDEA
- Right click the "src" directory -> Mark Directory As -> Sources Root
- Navigate to "src/main/kotlin/Main.kt". Then click Configure, when "Kotlin not configured is shown.
- Then press run or execute:
> .\gradlew run

Alternatively:
- Open in IntelliJ IDEA
- File -> Project Structure
- Under Project Settings > Modules
- Under 'Sources' tab, right-click on 'src' folder and select 'Sources'
- Apply Changes
- Then press run or execute:
> .\gradlew run

## Database Management

When the steps above have been followed, the application should execute and the Login Page should be displayed. 
There is no need to configure / set up the database as the application automatically does this. 
If the database does not exist, it is created and the application creates the tables and the data.
If the database is missing tables, the database is reset.

### Reload Database

To reset the database, the database can either be deleted, then the application will automatically create a new one.

OR

running python with the argument "-r" or "--reload-db" will reset the database

> .\gradlew run --args='-r'
> 
> .\gradlew run --args='--reload-db'

This is handy for debugging when wanting to start with a fresh db per execution.

## Useful default hard-coded information while running:

```
Accounts:
    Username: John
        Password: 123
        Contact Information: john@outlook.com
        Admin: No

    Username: Steve
        Password: 456
        Contact Information: steve@hotmail.co.uk
        Admin: Yes
 
    Username: Bob
        Password: 789
        Contact Information: bob@solent.ac.uk
        Admin: No
 
    University: Solent
    Buildings:
        Name: The Spark
        Code: TS
        Rooms:
            Room Number: 101
            Type: Windows Room
            Computers: 10
 
            Room Number: 202:
            Type: Linux Room:
            Computers: 8
 
        Name: Herbert Collins
        Code: HS
        Rooms:
            Room Number: 303
            Type: Mac Room
            Computers: 2
 
    Bookings:
        John booked at TS101-1 on Monday 9am-11am
        John booked at TS101-3 on Monday 9am-11am
        Bob booked at TS101-1 on Monday 11am-1pm
        Bob booked at TS101-4 on Monday 11am-1pm
```
