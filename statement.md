# Project Statement

## Project

Smart Library Management System

## 1. Problem Statement

Managing books, library members, book issues, returns and overdue fines by hand can take a lot of time. It also increases the chance of mistakes when keeping records.

The Smart Library Management System is built to automate these library tasks. The system offers a way to handle books and members keep track of book loans and returns calculate overdue fines and store data securely using a SQLite database.

## 2. Project Scope

This project is about building a console-based application for library management using Java.

The system includes:

- Managing books

- Managing members

- Issuing and returning books

- Tracking loans

- Calculating fines

- Storing data in a database

- Handling data through files

- Watching for overdue books

The project is meant for small or medium libraries. It is also designed as a project to show real-world use of Java programming skills.

## 3. Target Users

The main users of this system are:

- Librarians

- Library staff

- Library administrators

The system helps them keep library records up to date and carry out common library work quickly and clearly.

## 4. High-Level Features

### Book Management

- Add books to the library

- Look for books by title, author or ID

- Update book details like title, author or availability

- Remove books from the system

- Keep track of how many copies are in stock and how many are available

### Member Management

- Add new library members

- Search for members by name or ID

- Change member information like contact details

- Remove member records when needed

### Loan Management

- Issue books to members

- Record the date a book is issued and the due date

- Mark books as returned

- Show the status of a loan

- Save a history of all issued and returned books

### Fine Management

- Find out which loans are overdue

- Calculate fines based on how many days a book is late

- Save fine records for each overdue book

### Data Management

- Store all data in a SQLite database

- Use JDBC to connect and perform operations on the database

- Use file handling to manage book data when needed

### Monitoring

- Run a background process using multithreading to check for books regularly

## 5. Expected Outcome

The goal is to deliver an well-organized library management application. It helps reduce the need for record keeping and makes managing books, members and loans much easier.

The project also shows how to use Java concepts like Object-Oriented Programming, inheritance, abstraction, interfaces, exception handling, file handling, multithreading, JDBC, SQL and database management in a real situation.

## 6. Technology Stack

- Java

- Maven

- JDBC

- SQLite

- SQL

- Git

- GitHub

## 7. Project Type

This is a console-based Java application created for the Programming in Java course.

## 8. Future Scope

The system can be improved in the future with:

- A graphical user interface using JavaFX

- Login system with different user roles

- Integration with JPA or Hibernate

- Online booking of books

- Sending email alerts for books

- Detailed reports on library activity

- Storing data in the cloud, via a cloud-based database