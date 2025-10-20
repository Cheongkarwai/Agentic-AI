# Agentic AI - Parking Slot Booking System

A Spring Boot application that leverages LangChain4j and OpenAI for intelligent parking slot booking using AI agents.

## Overview

This project implements an agentic AI system for parking slot reservations, utilizing multiple specialized agents to handle availability checking, reservation management, and payment processing.

## Technology Stack

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring WebFlux** - Reactive web framework
- **Spring Data R2DBC** - Reactive database access
- **PostgreSQL** - Database
- **Redis** - Caching layer for parking slots and bookings
- **LangChain4j 1.4.0** - AI agent framework
- **OpenAI GPT-4** - Language model
- **Lombok** - Code generation
- **Project Reactor** - Reactive programming

## Features

- AI-powered parking slot booking system
- Reactive, non-blocking architecture
- Multiple specialized agents:
  - **AvailabilityAgent** - Checks parking slot availability
  - **ReservationAgent** - Manages booking reservations
  - **PaymentAgent** - Processes payments
- RESTful API for booking management
- Validation for booking requests
- Redis caching for improved performance:
  - Parking slot availability caching
  - Booking slot caching with TTL
- Redis key expiration listener for automatic booking cleanup
- Background processors for expired booking management

## Prerequisites

- Java 21 or higher
- PostgreSQL database
- Redis server
- OpenAI API key
- Maven

## Configuration

Set the following environment variables:

```bash
export OPENAI_API_KEY=your_openai_api_key
export OPENAI_BASE_URL=your_openai_base_url
export DATASOURCE_USERNAME=postgres
export DATASOURCE_URL=r2dbc:postgresql://localhost:5432/booking
export REDIS_HOST=localhost
export REDIS_PORT=6379
export REDIS_PASSWORD=your_redis_password
```

The application uses environment variables for configuration. See `src/main/resources/application.yaml` for all available configuration options including cache prefixes and logging levels.

## Installation

1. Clone the repository
2. Set all required environment variables (see Configuration section)
3. Start PostgreSQL and Redis servers
4. Run the application:

```bash
./mvnw spring-boot:run
```

The application will start on port 8081.

## API Endpoints

### Book a Parking Slot

**POST** `/api/v1/booking`

Request body:
```json
{
  "userId": "string",
  "duration": 60,
  "other_fields": "..."
}
```

## Project Structure

```
src/main/java/com/cheong/agenticai/
├── agent/              # AI agents
├── config/             # Configuration classes (Redis, listeners)
├── controller/         # REST controllers
├── dto/                # Data transfer objects
├── model/              # Domain models
├── processor/          # Background processors (booking cleanup)
├── repository/         # Data repositories
├── service/            # Business logic and cache services
├── tool/               # Agent tools
└── validator/          # Custom validators
```

## Database Models

- **User** - User information
- **ParkingSlot** - Parking slot details
- **BookingSlot** - Booking records
- **Transaction** - Payment transactions

## Development

Build the project:
```bash
./mvnw clean install
```

Run tests:
```bash
./mvnw test
```

## License

This project is licensed under the terms specified in the pom.xml file.
