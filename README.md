# Flight Search API

This project includes a simple RESTful API for managing flight information.

## API Endpoints

### List Flights

**Endpoint:** `GET /flights`

Lists all flights.

**Query Parameters:**
- `departureAirportId`: ID of the departure airport
- `arrivalAirportId`: ID of the arrival airport
- `departureDate`: Departure date (yyyy-MM-dd)
- `returnDate`: Return date (yyyy-MM-dd, for round-trip flights only)

## How to Run

To run the Flight Service API locally, follow these steps:

1. **Clone the Repository:**

    ```bash
    git clone https://github.com/username/flight-service.git
    ```

2. **Build and Run the Project:**

    Use the provided Maven wrapper to build and run the project:

    ```bash
    ./mvnw spring-boot:run
    ```

    The project will start, and you can access it at `http://localhost:8080`.

3. **Access Swagger UI:**

    Open your web browser and go to [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) to explore the API documentation using Swagger.

4. **Example Usage:**

    "http://localhost:8080/flights?departureAirportId=1&arrivalAirportId=2&departureDate=2024-01-01"

5. **Test the API Endpoints:**

    You can test the API endpoints using tools like cURL or Postman. Here is an example using cURL:

    ```bash
    curl -X GET "http://localhost:8080/flights?departureAirportId=1&arrivalAirportId=2&departureDate=2024-01-01" -H "accept: application/json"
    ```

6. **Explore API Documentation:**

    Additionally, you can explore the API documentation at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) when the application is running.

7. **Development Environment:**

    If you want to work on the project, you can use an integrated development environment (IDE) like Spring Tool Suite or IntelliJ IDEA.

Now you have the Flight Service API up and running on your local machine!

