# Polling API

Polling API is a Spring Boot-based RESTful service that allows users to create, publish, and vote on polls. The application supports user authentication, poll management, and vote tracking.

## Features

1. **User Authentication**
    - User registration and login.
    - Secure authentication using JWT.

2. **Poll Management**
    - Create, update, and delete polls.
    - Add multiple options to polls.
    - Publish or unpublish polls.

3. **Voting System**
    - Users can vote on published polls.
    - Vote counts are updated in real-time.
    - Prevents duplicate voting by the same user on the same poll.

4. **User Role Management**
    - Role-based access control (e.g., Admin and Regular User).

5. **Error Handling**
    - Comprehensive error messages for invalid requests.

## Technologies Used

- **Backend**: Spring Boot 3.x
- **Database**: MySQL (or any compatible relational database)
- **Security**: Spring Security with JWT
- **ORM**: Hibernate
- **Build Tool**: Maven

## Installation

### Prerequisites

- Java 17 or higher
- Maven 3.x
- MySQL or any compatible database

### Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/SpringBoot-PollingApp.git
   cd SpringBoot-PollingApp
   ```

2. **Configure the Database**
    - Update the `application.properties` file in `src/main/resources` with your database configuration:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/polling_api
      spring.datasource.username=your_username
      spring.datasource.password=your_password
      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.show-sql=true
      spring.jpa.properties.hibernate.format_sql=true
      ```

3. **Build the Project**
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**
    - The application will be available at: `http://localhost:8080`

## API Endpoints

### Authentication

| HTTP Method | Endpoint     | Description             |
|-------------|--------------|-------------------------|
| POST        | /auth/signup | Register a new user     |
| POST        | /auth/login  | Authenticate a user     |

### Poll Management

| HTTP Method | Endpoint               | Description                           |
|-------------|------------------------|---------------------------------------|
| POST        | /polls                | Create a new poll                     |
| GET         | /polls                | Get a list of all polls               |
| GET         | /polls/{pollId}       | Get details of a specific poll        |
| PUT         | /polls/{pollId}       | Update a specific poll                |
| DELETE      | /polls/{pollId}       | Delete a specific poll                |

### Voting

| HTTP Method | Endpoint               | Description                           |
|-------------|------------------------|---------------------------------------|
| POST        | /votes/{optionId}     | Cast a vote for a specific option     |

## Database Schema

### Tables

1. **users**
    - Fields: `id`, `username`, `email`, `password`, `roles`

2. **polls**
    - Fields: `id`, `title`, `description`, `created_by`, `expiration_date`, `is_published`

3. **options**
    - Fields: `id`, `text`, `poll_id`, `vote_count`

4. **votes**
    - Fields: `id`, `user_id`, `option_id`

### Relationships

- A `User` can create multiple `Polls`.
- A `Poll` can have multiple `Options`.
- A `User` can vote on multiple `Options`, but only once per poll.


## Common Errors

1. **LazyInitializationException**: Ensure that all lazy-loaded collections are accessed within a transaction.
2. **EntityNotFoundException**: Check if the requested resource exists before performing operations.

## Contact

For any inquiries, reach out to:
- **Author**: Ayoub Debbagh
- **Email**: ay.debbagh@gmail.com
- **LinkedIn**: [Ayoub Debbagh](https://www.linkedin.com/in/ayoubdebbagh)
- **GitHub**: [Ayoub Debbagh](https://www.github.com/ayoub-debbagh)

