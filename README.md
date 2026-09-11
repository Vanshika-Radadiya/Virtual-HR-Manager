# Virtual HR Manager

A full-stack interview system that helps automate candidate screening using a Java Spring Boot backend, Angular frontend, and MySQL database.

## Overview

This project simulates a virtual interview experience where a candidate answers questions, records voice-based responses, and receives follow-up questions dynamically based on their answers. The system evaluates responses, tracks interview progress, and sends a completion email after the session ends.

## Features

- Candidate interview flow with role-based interview sessions
- Dynamic follow-up question generation based on answer quality
- Topic-based question banks for OOP, OS, and DBMS
- Voice/audio response recording support in the frontend
- Interview result tracking and scoring
- MySQL persistence for interviews, users, records, and results
- Email notification on interview completion

## Tech Stack

- Backend: Java, Spring Boot, Spring Data JPA, MySQL
- Frontend: Angular
- Database: MySQL
- Build Tools: Maven, Angular CLI

## Project Structure

- `HR_Backend` – Spring Boot REST API and business logic
- `HR_Frontend` – Angular application for the user interface

## Prerequisites

- Java 17+
- Maven
- Node.js and npm
- MySQL server

## Setup

1. Create a MySQL database named `virtualhr`.
2. Update database credentials in `HR_Backend/src/main/resources/application.properties` if needed.
3. Start the backend:

   ```bash
   cd HR_Backend
   ./mvnw spring-boot:run
   ```

4. Start the frontend:

   ```bash
   cd HR_Frontend
   npm install
   ng serve
   ```

5. Open the Angular app in the browser, usually at `http://localhost:4200`.
