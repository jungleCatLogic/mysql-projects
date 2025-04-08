<div align="center">

  <h1>DIY Projects Manager</h1>
  
  <p>
    A MySQL-based Java application for tracking and managing DIY projects
  </p>

<p>
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/Eclipse-2C2255?style=for-the-badge&logo=eclipse&logoColor=white" alt="Eclipse" />
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven" />
</p>
   
</div>

<!-- Table of Contents -->
# Table of Contents

- [About the Project](#about-the-project)
  * [Features](#features)
  * [Tech Stack](#tech-stack)
  * [Database Schema](#database-schema)
- [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
  * [Configuration](#configuration)
- [Usage](#usage)
  * [Menu Options](#menu-options)
  * [Project Creation](#project-creation)
  * [Project Management](#project-management)
- [Project Structure](#project-structure)
- [Design Patterns](#design-patterns)

<!-- About the Project -->
## About the Project

DIY Projects Manager is a menu-driven console application that helps users track and manage their DIY (Do-It-Yourself) projects. It demonstrates full CRUD (Create, Read, Update, Delete) operations with a MySQL database backend, implementing a three-tier architecture pattern.

### Features

- **Create**: Add new DIY projects with details like project name, estimated hours, actual hours, difficulty level, and notes
- **Read**: 
  - View a list of all projects
  - Select and display details for a specific project including materials, steps, and categories
- **Update**: Modify project details while maintaining data integrity
- **Delete**: Remove projects with automatic cascading deletion of associated materials, steps, and categories

### Tech Stack

- **Java**: Core programming language
- **MySQL**: Database for storing project information
- **JDBC**: API for connecting and executing queries with the database
- **Maven**: Dependency management and build automation
- **DBeaver**: Database management tool used for direct database access

### Database Schema

The application uses the following tables:
- `project`: Main project information (name, hours, difficulty, notes)
- `material`: Materials needed for each project
- `step`: Sequential steps to complete a project
- `category`: Project categories (e.g., Woodworking, Gardening)
- `project_category`: Junction table connecting projects to categories

All child tables have foreign key constraints with ON DELETE CASCADE to maintain referential integrity when a project is deleted.

<!-- Getting Started -->
## Getting Started

### Prerequisites

- Java 8 or higher
- MySQL 5.7 or higher
- Maven
- Eclipse IDE (recommended)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/junglecatlogic/mysql-projects.git
cd mysql-projects
```

2. Set up the database:
```bash
# Login to MySQL
mysql -u root -p

# Create database and user (in MySQL shell)
CREATE DATABASE projects;
CREATE USER 'projects_user'@'localhost' IDENTIFIED BY 'your_secure_password'; // Replace with actual password
GRANT ALL PRIVILEGES ON projects.* TO 'projects_user'@'localhost'; // Replace with actual user name 
FLUSH PRIVILEGES;
EXIT;

# Import schema
mysql -u projects_user -p projects < projects-schema.sql // Replace with your actual user name
```

3. Build the project:
```bash
mvn clean package
```

### Configuration

If needed, modify the database connection settings in `DbConnection.java`:

```java
private static final String HOST = "localhost";
private static final String PASSWORD = "your_password_here"; // Replace with your actual password
private static final int PORT = 3306;
private static final String SCHEMA = "projects";
private static final String USER = "projects_user"; // Replace with your actual username
```

<!-- Usage -->
## Usage

### Menu Options

Run the application and you'll see a menu with these options:

```
These are the available selections. Press the Enter key to quit:
  1) Add a project
  2) List projects
  3) Select a project
  4) Update project details
  5) Delete a project
```

### Project Creation

To create a new project:
1. Select option `1) Add a project`
2. Enter the project name
3. Provide estimated and actual hours (decimal values)
4. Set difficulty level (1-5)
5. Add notes about the project

### Project Management

- To view all projects, select option `2) List projects`
- To work with a specific project, select option `3) Select a project` and enter the project ID
- To update a selected project, use option `4) Update project details`
- To delete a project, select option `5) Delete a project`

<!-- Project Structure -->
## Project Structure

The application follows a three-tier architecture:

```
mysql-projects/
├── pom.xml                  # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── projects/
│   │   │   │   ├── ProjectsApp.java    # Main application class (presentation layer)
│   │   │   │   ├── dao/
│   │   │   │   │   ├── DbConnection.java  # Database connection management
│   │   │   │   │   └── ProjectDao.java    # Data access layer
│   │   │   │   ├── entity/
│   │   │   │   │   ├── Category.java      # Entity classes
│   │   │   │   │   ├── Material.java
│   │   │   │   │   ├── Project.java
│   │   │   │   │   └── Step.java
│   │   │   │   ├── exception/
│   │   │   │   │   └── DbException.java   # Custom exception handling
│   │   │   │   └── service/
│   │   │   │       └── ProjectService.java  # Service layer
│   │   │   └── provided/
│   │   │       └── util/
│   │   │           └── DaoBase.java     # Utility class for database operations
├── projects-schema.sql      # Database schema creation script
```

<!-- Design Patterns -->
## Design Patterns

The application implements several design patterns:

1. **Three-Tier Architecture**: 
   - Presentation Layer (ProjectsApp.java)
   - Service Layer (ProjectService.java)
   - Data Access Layer (ProjectDao.java)

2. **DAO Pattern**: Data Access Objects encapsulate database operations and provide a clean API for the service layer

3. **Transaction Management**: Proper transaction handling ensures data integrity

4. **Exception Handling**: Custom exceptions provide meaningful error messages and proper error propagation
