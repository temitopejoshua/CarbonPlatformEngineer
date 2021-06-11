Technology Stack: Java, Spring-boot
Database : H2 Database was used to avoid any database configuration
Port : Application runs on port 8089

Data has been preloaded in the h2 database on application startup 

You can make a call to this endpoint to fetch all preloaded reservations

http://localhost:8089/api/v1/reservations/

You can make a call to this endpoint to fetch preloaded reservation by id`

http://localhost:8089/api/v1/reservations/{id}


