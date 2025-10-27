# Test Assessment

## Overview
This project is a RESTful API built with Spring Boot for managing delivery boxes and their loaded items.  
It allows creating boxes, loading items, checking available boxes, retrieving loaded items, and monitoring battery levels.  
The backend uses PostgreSQL for data persistence.

Tech Stack
- Language: Java (Spring Boot 3)
- Database: PostgreSQL
- Build Tool: Maven
- ORM: Hibernate (JPA)
- Port: 8080


Project Setup

### 1. Clone the Repository

git clone https://github.com/Dumebimoses/Test-Assessment.git
cd test-assessment

### 2. Configure Database

Create a PostgreSQL database named:

test_assessment


Then update your src/main/resources/application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/test_assessment
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

### 3. Run the Application
mvn spring-boot:run


The application will start at:

http://localhost:8080

1. Create a Box

POST /api/boxes/create
Request Body:

{
  "txref": "TXN12345",
  "weightLimit": 400,
  "batteryCapacity": 80
}


Response:

{
  "txref": "TXN12345",
  "weightLimit": 400,
  "batteryCapacity": 80,
  "state": "IDLE"
}

2. Load Box with Items

POST /api/boxes/{txref}/items
Request Body:

[
  {
    "name": "Item A",
    "weight": 100,
    "code": "A100"
  },
  {
    "name": "Item B",
    "weight": 150,
    "code": "B150"
  }
]


Response:

{
  "txref": "TXN12345",
  "weightLimit": 400,
  "batteryCapacity": 80,
  "state": "LOADED",
  "currentWeight": 250,
  "items": [
    {
      "id": 1,
      "name": "Item A",
      "weight": 100,
      "code": "A100"
    },
    {
      "id": 2,
      "name": "Item B",
      "weight": 150,
      "code": "B150"
    }
  ]
}

3. Get Loaded Items

GET /api/boxes/{txref}/items
Response:

[
  {
    "id": 1,
    "name": "Item A",
    "weight": 100,
    "code": "A100"
  },
  {
    "id": 2,
    "name": "Item B",
    "weight": 150,
    "code": "B150"
  }
]

4. Get Available Boxes

GET /api/boxes/available
Response:

[
  {
    "txref": "BOX1001",
    "weightLimit": 500,
    "batteryCapacity": 80,
    "state": "IDLE",
   "currentWeight": 0
  }
]

5. Check Battery Level

GET /api/boxes/{txref}/battery
Response:

{
  "txref": "BOX1001",
  "batteryCapacity": 80
}

