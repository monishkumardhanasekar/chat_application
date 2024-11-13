# Real-Time Chat Application

This project is a real-time chat application that uses **WebSocket**, **STOMP**, and **RabbitMQ** to enable group-based messaging. Users can join different groups, send messages, and receive updates in real-time.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [Using Multiple Ports](#using-multiple-ports)
- [Verifying and Testing](#verifying-and-testing)
- [Troubleshooting](#troubleshooting)

## Technologies Used

- **Spring Boot**: Framework for building the backend application.
- **RabbitMQ**: Message broker that routes messages between the server and users.
- **WebSocket**: Protocol for two-way, real-time communication between the client and server.
- **STOMP** (Simple Text Oriented Messaging Protocol): Messaging protocol used with WebSocket to manage subscriptions and route messages to specific topics/groups.

## Project Structure

The application is divided into the following parts:

- **Backend Configuration** (`WebSocketConfig.java`): Sets up WebSocket with RabbitMQ using STOMP for handling real-time message subscriptions.
- **JavaScript Frontend** (`main.js`): Manages user interactions, group subscriptions, and WebSocket connections from the browser.

## Setup and Installation

### Clone the Repository

```bash
git clone https://github.com/monishkumardhanasekar/chat-application.git
cd chat-application
```

### Set Up RabbitMQ

Ensure RabbitMQ is installed and running on your system. The default configuration expects RabbitMQ to be accessible on `localhost:61613` with the default guest user credentials.

### Install Dependencies

This project uses Spring Boot with Maven. Install all dependencies by running:

```bash
./mvnw clean install
```

## Running the Application

### To run the application on the default port (8080):

```bash
./mvnw spring-boot:run
```

### Running on a Custom Port

To start the application on a different port, use the `server.port` argument:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8082"
```

This command starts the server on `http://localhost:8082` and connects the WebSocket and RabbitMQ relay to the same port. This is useful if you want to run multiple instances of the application on different ports for testing.

## Using Multiple Ports

You can run multiple instances of the application on different ports by specifying a unique `server.port` value each time.

- **Start the First Instance**:
  ```bash
  ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8082"
  ```
- **Start a Second Instance on a Different Port (e.g., 8083)**:
  `bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8083"
`
  Each instance will function independently but still connect to RabbitMQ for real-time messaging.

## Verifying and Testing

To verify the application is running correctly:

- **Check Application Status**:

  - Open a browser and navigate to `http://localhost:8080` (or your specified port).
  - You should see the chat interface allowing you to create, join, and leave chat groups.

- **Subscribe and Send Messages**:

  - Open multiple browser windows and use different usernames to join the same group.
  - Test message sending to verify that messages appear in real-time across all users in the group.

- **Check RabbitMQ Connection**:
  - Confirm that messages are being routed through RabbitMQ.
  - You can monitor RabbitMQ activity by accessing its management console at `http://localhost:15672` (default port) if enabled.

## Troubleshooting

- **RabbitMQ connection errors**: Ensure RabbitMQ is running and accessible on `localhost:61613`. Check RabbitMQ logs if issues persist.
- **WebSocket subscription errors**: Verify the WebSocket endpoints in both the backend (`WebSocketConfig.java`) and frontend (`main.js`).

---
