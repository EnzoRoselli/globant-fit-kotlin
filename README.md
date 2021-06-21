# Globant Fit Excercise (In Kotlin) <img src="https://cdn.jsdelivr.net/npm/programming-languages-logos/src/kotlin/kotlin.png" height="25">

The excercise was to develop an application that give us available flights for a given search query. With an inventory of the following flights:

```
{
  "flights": [
    {
      "flight": "Air Canada 8099",
      "departure": "7:30AM"
    },
    {
      "flight": "United Airline 6115",
      "departure": "10:30AM"
    },
    {
      "flight": "WestJet 6456",
      "departure": "12:30PM"
    },
    {
      "flight": "Delta 3833",
      "departure": "3:00PM"
    }
  ]
}

```

## Stack: :computer:

- Kotlin 1.5.10 (Compatibility with Java 16)
- Spring Boot 2.5.1
- Gradle 7.0.2
- MySQL8
- Swagger
- JUnit 5
- MockK
- Kotest (Only the Assertions Library)

## How to Use :pencil:

- You will need to create a local database in MySQL with the name you wish, then run the queries from **schema.sql**
- In **application.yml** you have the Environment Variables **DB_NAME**, **DB_USERNAME** and **DB_PASSWORD**
- Configure the Environment Variables in your IDE and then run the application.
- Open a browser a type localhost:8080/flights/?departure=hh:mma where *hh* is the hour, *mm* minutes, and *a* AM/PM. E.g. /flights/?departure=07:30AM
- Use Swagger to access all the endpoints : http://localhost:8080/swagger-ui.html
- Also, you can see the swagger online here: https://app.swaggerhub.com/apis/EnzoRoselli/globant-fit-kotlin/1.0.0 
- You should see some flights 5 hours before & after the time you wrote.
- At the root of the project a file named fitFlightApplication.log will be created which serves as the log for the application.

---
By Enzo Roselli
