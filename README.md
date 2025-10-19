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

## Prerequisites

- Java 21 or higher
- PostgreSQL database
- OpenAI API key
- Maven

## Configuration

Set the following environment variables:

```bash
export OPENAI_API_KEY=your_openai_api_key
export OPENAI_BASE_URL=your_openai_base_url
```

Update `src/main/resources/application.yaml` with your database credentials:

```yaml
spring:
  r2dbc:
    username: postgres
    url: r2dbc:postgresql://localhost:5432/booking
```

## Installation

1. Clone the repository
2. Set environment variables for OpenAI
3. Configure database connection in `application.yaml`
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
├── config/             # Configuration classes
├── controller/         # REST controllers
├── dto/                # Data transfer objects
├── model/              # Domain models
├── repository/         # Data repositories
├── service/            # Business logic
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
