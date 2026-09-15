  Smart Library Management System


1. Project Overview

The Smart Library Management System is a Java based application designed to manage books library members librarians and book loans efficiently

The project shows Java programming concepts such as Object Oriented Programming inheritance encapsulation abstraction interfaces exception handling collections file handling multithreading JDBC and SQLite database operations



2. Problem Statement

Traditional library management can involve record keeping for books members borrowing returning and overdue fines

This project provides a simple computerized system that allows library staff to manage these operations while maintaining persistent records using files and an SQLite database


3. Objectives

- Manage library. Their copies

- Register and manage library members

- Manage librarian information

- Search for books and members

- Issue and return books

- Track available book copies

- Calculate overdue fines

- Handle invalid operations using exceptions

- Store library data persistently

- Demonstrate multithreading using an overdue monitoring service

- Use SQLite and JDBC for database persistence



4. Main Features

## Book Management

- Add books

- Search books

- Update book information

- Delete books

- Track total and available copies

- Display book status

## Member Management

- Add members

- Search members

- Update members

- Delete members

- Support Standard and Premium membership types

## Loan Management

- Issue books

- Return books

- Track issue and due dates

- Track return dates

- Calculate fines

- Maintain loan status

## Exception Handling

The application handles situations such as:

- Book not found

- Member not found

- No available copies

- Attempting to return a returned book

- Invalid database operations

## Persistence

The project uses:

- Text file storage for book data

- SQLite database for structured persistent data

- JDBC, for database connectivity

## Multithreading

An overdue monitor runs as a thread and periodically checks for overdue library loans



5. Technologies Used

- Java

- Maven

- JDBC

- SQLite

- SQL

- Git

- GitHub



6. Java Concepts Demonstrated

### Object-Oriented Programming

- Encapsulation

- Inheritance

- Abstraction

- Polymorphism

### Java Concepts

- Classes and objects

- Constructors

- Interfaces

- Enumerations

- Exception handling

- Collections

- File handling

- Date and time API

- Multithreading

- JDBC




7. Project Structure

smart-library-management-system/

│

├── pom.xml

├── README.md

├──.gitignore

│

├── data/

│   ├── books.txt

│   └── library.db

│

├── docs/

│

└── src/

├── main/

│   ├── java/

│   │   └── com/library/

│   │       ├── Main.java

│   │       │

│   │       ├── database/

│   │       │

│   │       ├── dao/

│   │       │

│   │       ├── exception/

│   │       │

│   │       ├── model/

│   │       │

│   │       ├── repository/

│   │       │

│   │       ├── service/

│   │       │

│   │       └── util/

│   │

│   └── resources/

│

└── test/

└── java/