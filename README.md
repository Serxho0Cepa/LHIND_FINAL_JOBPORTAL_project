Job Portal Application A modern REST API-based job portal system where employers can post jobs and job seekers can apply for positions.

Overview This application is built using Spring Boot and provides a comprehensive job portal system with role-based access control (Admin, Employer, Job Seeker).

Key Features • User Management: Registration and authentication for three user roles • JWT Authentication: Secure token-based authentication • Employer Features: o Company profile management o Job posting and management o Review job applications o Update application status • Job Seeker Features: o Profile management o Resume upload o Job application submission o View application status • Admin Features: o User management o System oversight

Entity Structure • User: Central authentication entity (implements UserDetails) • Employer: Company information and job listings • JobSeeker: Personal details and resume management • Job: Job postings with details • JobApplication: Tracks applications and their status • JobReview: Rating system for jobs

API Endpoints Authentication • POST /api/auth/login - User login • PUT /api/auth/verify - Verify user account User Management • POST /api/users/register - Register new user • GET /api/users - List users (Admin) • DELETE /api/users/{id} - Delete user (Admin) Employer Operations • POST /api/employers/{id}/profile - Create employer profile • GET /api/employers/search - Search employers • POST /api/employers/{id}/jobs - Post a new job • GET /api/employers/{id}/jobs - Get employer's posted jobs • GET /api/employers/{employerId}/jobs/{jobId}/applications - Get applications for a job • PATCH /api/employers/applications/{id}/status - Update application status Job Seeker Operations • POST /api/jobseekers/{id}/resume - Upload resume • GET /api/job-seekers/{id}/applications - Get job seeker's applications Technical Implementation • Spring Security: Role-based access control • JWT Authentication: Stateless authentication • JPA/Hibernate: Database mapping with proper relationships • DTOs: Clean data transfer between layers • RESTful Design: Proper HTTP methods and response codes

SETUP Clone the repository from: Configuration Database Configuration Configure the database settings in src/main/resources/application.properties:

Database Configuration
JPA/Hibernate Configuration
JWT Configuration
File Upload Configuration
Security Configuration The application is configured with Spring Security and JWT authentication: • SecurityConfig: Configures security settings including URL access rules and JWT authentication • JwtAuthenticationFilter: Intercepts requests to validate JWT tokens • JwtUtil: Handles JWT token creation and validation • CustomUserDetailsService: Loads user-specific data during authentication Building and Running the Application Maven Commands

Clean the project
mvn clean

Compile and package the project
mvn install

Run the application
Testing the API After starting the application, the API will be accessible at http://localhost:8080. You can use tools like Postman to test the endpoints (a Postman collection is included in the project files). Initial Setup

Register an admin user using /api/users/register
Verify the user if email verification is enabled
Login using /api/auth/login to get a JWT token
Use this token in the Authorization header for subsequent requests:
Authorization: Bearer your_jwt_token_here
