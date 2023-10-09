# YSD_B03_J2EE_MidTerm_StackOverflow
Welcome to the Personalized Health and Wellness Recommendation System project repository. This innovative system employs a microservices architecture and leverages modern technologies to provide tailored health and wellness recommendations, facilitate progress tracking, encourage community engagement, offer nutrition information, support mental health, utilize data analytics, and provide feedback analysis.

## Project Overview

In this project, we have developed a comprehensive backend-only system with the following key features and components:

### Microservices Architecture

Our system is built on a microservices architecture, ensuring modularity and scalability. Each microservice addresses specific aspects of health and wellness recommendations and data management.

### Spring Boot

Spring Boot serves as the foundational framework for developing our microservices. Its capabilities expedite the creation of robust and scalable backend services.

### JPA (Java Persistence API)

JPA facilitates data persistence and interaction with a relational database. Entities representing user profiles, health data, recommendations, feedback, and community interactions are defined and managed using JPA.

### API Gateway

Our API Gateway centralizes request routing.

### Discovery Server

The Discovery Server manages service registration and discovery for microservices. It enables dynamic scaling and load balancing, ensuring system reliability.

### Security Service

The Authentication Service handles user registration, login. Secure user authentication and access token generation are implemented.

### PostgreSQL Database

Security, User, and Recommendation microservices use PostgreSQL as the database for enhanced data security and reliability.

### MySQL Database

Other microservices utilize MySQL for efficient data storage and retrieval.

### WebClient and Auto-trigger Retry Mechanism

Microservices communicate with each other using WebClient, enabling efficient microservice-to-microservice communication. An auto-trigger retry mechanism ensures data consistency.

### Circuit Breaker and Fallback Methods

The system incorporates a circuit breaker and fallback methods to enhance fault tolerance and resilience.

### Microservices Architecture

Our system is built on a microservices architecture, ensuring modularity and scalability. Each microservice addresses specific aspects of health and wellness recommendations and data management.

1. **User Service**
   - API: `/users`
   - Endpoints:
     - `/register`: User registration.
     - `/login`: User login.
     - `/profile`: Manage user profile information.
     - `/health-data`: Manage personal health data.

2. **Recommendation Service**
   - API: `/recommendations`
   - Endpoints:
     - `/diet`: Get personalized diet recommendations.
     - `/exercise`: Receive exercise routines.
     - `/mental-health`: Access mental health exercises.
     - `/sleep`: Receive sleep schedule recommendations.

3. **Feedback Service**
   - API: `/feedback`
   - Endpoints:
     - `/submit`: Submit feedback on recommendations.

4. **Progress Tracking Service**
   - API: `/progress`
   - Endpoints:
     - `/track`: Track health and wellness progress.
    - `/insights`: View insights based on progress data.

5. **Notification Service**
   - API: `/notifications`
   - Endpoints:
     - `/preferences`: Configure notification preferences.
     - `/send`: Send personalized recommendations via notifications.

6. **Community Service**
   - API: `/community`
   - Endpoints:
     - `/groups`: Create or join wellness groups.
     - `/posts`: Share achievements and wellness updates.
     - `/interactions`: Like, comment, and follow other users' posts.

7. **Nutrition Information Service**
   - API: `/nutrition`
   - Endpoints:
     - `/search`: Search for foods and recipes.
     - `/details`: Retrieve nutritional facts for specific foods.
     - `/recommendations`: Receive dietary recommendations.

8. **Mental Health Service**
   - API: `/mental-health`
   - Endpoints:
     - `/exercises`: Access mental health exercises.
     - `/mood-tracking`: Log and track mood data.
     - `/recommendations`: Receive mental health recommendations.

9. **Data Analysis Service**
   - API: `/ml`
   - Endpoints:
     - `/analyze`: Analyze data from DB directly.

## Getting Started

To get started with our Personalized Health and Wellness Recommendation System, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/BJITAcademy2021/YSD_B03_J2EE_MidTerm_StackOverflow.git
   cd YSD_B03_J2EE_MidTerm_StackOverflow
