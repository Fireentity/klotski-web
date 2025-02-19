# Klotski Web Game

A web-based implementation of the classic Klotski sliding block puzzle game. The game challenges players to move a special block to a specific position by strategically sliding other blocks out of the way, aiming to complete the puzzle in the minimum number of moves possible.

## Features

- User authentication system for game progress tracking
- Multiple difficulty levels (Simple, Medium, Hard, Impossible)
- Move counter to track puzzle solving progress
- Game controls:
  - Reset button to restart the current puzzle
  - Undo button for reverting the last move
  - Hint system for assistance when stuck
- History tracking of last 10 games with move counts and timestamps
- Ability to start a new game at any time

## Technical Stack

### Backend
- Java 17
- Spring Boot 3.0.2
- Gson 2.10.1
- Lombok 1.18.28
- JUnit
- Gradle 7.6

### Frontend
- TypeScript 5.0.4
- Vue 3.2.47
- Tailwind CSS 3.3.2
- Axios 1.3.6
- Vuex 4.1.0
- Vite 4.3.2
- Node.js 18.16.1

## Architecture

The project follows a clear separation between frontend and backend, implementing the following design patterns:
- Model-Controller (MC) architecture with API exposure
- Visitor pattern for tile behavior management
- Repository pattern for data persistence
- Strategy pattern for movement validation and JSON serialization

## Prerequisites

- Docker
- Amazon Corretto 17 (Amazon's distribution of OpenJDK 17)
- WSL2 (required for Windows users)
- IntelliJ IDEA (recommended IDE)

## Installation & Setup

1. Clone the repository from GitHub
2. Open the project in IntelliJ IDEA
3. Navigate to the `docker-compose.yml` file
4. Start the frontend and database services by clicking the green play button on lines 4 and 18 respectively

### Starting the Backend

1. Open the Gradle tool window in IntelliJ IDEA
2. Navigate to: `klotski-web/klotski-web/game-rest-api/Tasks/application`
3. Double-click on the `bootRun` executable

### Accessing the Application

1. Click on "Services" at the bottom left of IntelliJ IDEA
2. Select `klotski-web-frontend-1`
3. The application URL will be displayed

## Project Structure

The project is structured with a clear separation between frontend and backend components, allowing for potential frontend reimplementation (e.g., with JavaFX) while maintaining the same backend structure.
