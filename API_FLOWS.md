# Campus Connect API Flows

## Overview
This document outlines the API flows for the Campus Connect application, detailing how the React frontend interacts with the Spring Boot backend and Python microservices.

## Architecture
- **Frontend**: React app (port 3000)
- **Backend**: Spring Boot (port 8080)
- **ML Service**: Flask (port 5001)
- **GenAI Service**: Flask (port 5002)

## API Flows

### 1. User Authentication
- **Flow**: React -> Spring Boot
- **Endpoints**:
  - POST /api/auth/login
  - POST /api/auth/register
- **Description**: User logs in or registers via React form, which calls Spring Boot auth endpoints. JWT token is returned and stored in React state.

### 2. Dashboard Data Fetching
- **Flow**: React -> Spring Boot
- **Endpoints**:
  - GET /api/notifications
  - GET /api/materials
  - GET /api/interview-experiences
- **Description**: After login, React fetches user-specific data from Spring Boot, which queries MongoDB.

### 3. Upload Material
- **Flow**: React -> Spring Boot
- **Endpoints**:
  - POST /api/materials
- **Description**: User uploads study material via React form, sent to Spring Boot for storage in MongoDB.

### 4. Submit Interview Experience
- **Flow**: React -> Spring Boot -> GenAI Service
- **Endpoints**:
  - POST /api/interview-experiences (Spring Boot)
  - POST /api/genai/summarize (GenAI Service)
- **Description**: User submits interview experience, Spring Boot saves to DB and calls GenAI service to generate summary, which is stored with the experience.

### 5. AI Chatbot Query
- **Flow**: React -> Spring Boot -> GenAI Service
- **Endpoints**:
  - POST /api/chat (Spring Boot proxy)
  - POST /api/genai/chat (GenAI Service)
- **Description**: User asks placement-related question in chatbot, React sends to Spring Boot, which forwards to GenAI service for response.

### 6. Performance Prediction
- **Flow**: React -> Spring Boot -> ML Service
- **Endpoints**:
  - POST /api/predict (Spring Boot proxy)
  - POST /api/ml/predict (ML Service)
- **Description**: User inputs attendance and marks, React sends to Spring Boot, which calls ML service for risk prediction.

### 7. Notifications
- **Flow**: React -> Spring Boot
- **Endpoints**:
  - GET /api/notifications
  - POST /api/notifications (for sending notifications)
- **Description**: Fetch and display notifications; system can send notifications based on events.

## Error Handling
- All endpoints return appropriate HTTP status codes and error messages in JSON format.
- Frontend handles errors by displaying user-friendly messages.

## Security
- All API calls to Spring Boot require JWT token in Authorization header.
- Python services may require API keys or be secured via internal network.

## Testing Flows
- Use Postman or curl to test individual endpoints.
- For integration testing, run all services locally and use browser dev tools to monitor network requests.
