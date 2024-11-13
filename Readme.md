# Real-Time Chat Application

This project is a real-time chat application that uses **WebSocket**, **STOMP**, and **RabbitMQ** to enable group-based messaging. Users can join different groups, send messages, and receive updates in real-time.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [Using Multiple Ports](#using-multiple-ports)
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
git clone https://github.com/yourusername/chat-application.git
cd chat-application
```
