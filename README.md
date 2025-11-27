# Boulder Tracker Backend

A RESTful API for tracking bouldering progress, managing climbing projects, and analyzing performance metrics.

## Features

- Track boulder problems with detailed information (name, grade, location, status)
- Monitor climbing progress with three status types: PROJECT, SENT, and FLASHED
- Record attempts, notes, and beta for each climb
- Generate statistics including total sends, active projects, and grade distribution
- Visualize climbing pyramid data
- Filter climbs by status, location, and grade
- Comprehensive validation and error handling

## Tech Stack

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Data JPA** - Database operations and ORM
- **PostgreSQL** - Primary database
- **Lombok** - Reduce boilerplate code
- **Maven** - Dependency management and build tool
- **Jakarta Validation** - Request validation

## Project Structure

```
src/main/java/com/bouldering/tracker/
├── controller/          # REST API endpoints
├── service/            # Business logic layer
├── repository/         # Data access layer
├── model/              # Entity classes
├── dto/                # Data transfer objects
├── exception/          # Custom exceptions and global error handling
└── config/             # Configuration classes
```

## Getting Started

### Prerequisites

- Java 17 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher

### Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE boulder_tracker;
```

2. Create `application.properties` in `src/main/resources/`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/boulder_tracker
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Installation

1. Clone the repository:
```bash
git clone https://github.com/EmilioMonteLuna/Boulder-Tracker.git
cd Boulder-Tracker
```

2. Build the project:
```bash
./mvnw clean install
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

## API Endpoints

### Climb Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/climbs` | Get all climbs |
| GET | `/api/climbs/{id}` | Get climb by ID |
| POST | `/api/climbs` | Create new climb |
| PUT | `/api/climbs/{id}` | Update climb |
| DELETE | `/api/climbs/{id}` | Delete climb |
| GET | `/api/climbs/status/{status}` | Get climbs by status |
| GET | `/api/climbs/location/{location}` | Get climbs by location |
| GET | `/api/climbs/stats` | Get climbing statistics |
| GET | `/api/climbs/pyramid` | Get grade pyramid data |

### Example Requests

**Create a Climb:**
```json
POST /api/climbs
{
  "name": "The Groove",
  "grade": "V5",
  "location": "Brooklyn Boulders",
  "status": "PROJECT",
  "notes": "Work on heel hook sequence",
  "attempts": 3
}
```

**Update a Climb:**
```json
PUT /api/climbs/1
{
  "name": "The Groove",
  "grade": "V5",
  "location": "Brooklyn Boulders",
  "status": "SENT",
  "notes": "Finally got the beta dialed in!",
  "attempts": 12
}
```

### Response Format

**Success Response:**
```json
{
  "id": 1,
  "name": "The Groove",
  "grade": "V5",
  "location": "Brooklyn Boulders",
  "status": "SENT",
  "notes": "Finally got the beta dialed in!",
  "attempts": 12,
  "dateSent": "2025-11-27T10:30:00",
  "createdAt": "2025-11-20T14:00:00",
  "updatedAt": "2025-11-27T10:30:00"
}
```

**Statistics Response:**
```json
{
  "totalClimbs": 45,
  "totalSends": 32,
  "activeProjects": 13,
  "pyramid": [
    {"grade": "V3", "count": 8},
    {"grade": "V4", "count": 12},
    {"grade": "V5", "count": 7}
  ]
}
```

## Data Model

### Climb Entity

- `id` (Long) - Unique identifier
- `name` (String) - Name of the boulder problem
- `grade` (String) - V-scale grade (V0-V17)
- `location` (String) - Gym or outdoor crag
- `status` (ClimbStatus) - PROJECT, SENT, or FLASHED
- `notes` (String) - Beta and technique notes
- `photoUrl` (String) - Optional photo reference
- `attempts` (Integer) - Number of attempts
- `dateSent` (LocalDateTime) - Date completed
- `createdAt` (LocalDateTime) - Creation timestamp
- `updatedAt` (LocalDateTime) - Last update timestamp

### Climb Status

- `PROJECT` - Currently working on
- `SENT` - Successfully completed
- `FLASHED` - Sent on first attempt

## Validation

All climb creation and update requests are validated:
- Name, grade, location, and status are required fields
- Invalid status values return 400 Bad Request
- Missing required fields return detailed error messages

## CORS Configuration

The API is configured to accept requests from `http://localhost:5173` (frontend development server).

## Testing

Run the test suite:
```bash
./mvnw test
```

## Building for Production

Create a production-ready JAR:
```bash
./mvnw clean package
java -jar target/tracker-0.0.1-SNAPSHOT.jar
```

## Related Projects

- [Boulder Tracker Frontend](https://github.com/EmilioMonteLuna/boulder-tracker-frontend) - React-based web interface

## Future Enhancements

- Photo upload functionality
- User authentication and profiles
- Session tracking and analytics
- Social features (sharing climbs, following friends)
- Training plan generation
- Mobile application

## License

This project is licensed under the MIT License.