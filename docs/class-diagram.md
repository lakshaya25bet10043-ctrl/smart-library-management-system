# Smart Library Management System - Class Diagram

```mermaid
classDiagram

    class Person {
        <<abstract>>
        -int id
        -String name
        -String email
        +getId() int
        +getName() String
        +getEmail() String
        +getRole() String
        +displayInfo() void
    }

    class Member {
        -String membershipType
        +getMembershipType() String
        +setMembershipType(String) void
        +getRole() String
        +displayInfo() void
    }

    class Librarian {
        +getRole() String
        +displayInfo() void
    }

    class Book {
        -int bookId
        -String title
        -String author
        -String isbn
        -BookCategory category
        -int totalCopies
        -int availableCopies
        -BookStatus status
        +borrowBook() void
        +returnBook() void
        +getAvailableCopies() int
        +getStatus() BookStatus
    }

    class Loan {
        -int loanId
        -Member member
        -Book book
        -LocalDate issueDate
        -LocalDate dueDate
        -LocalDate returnDate
        -double fine
        -LoanStatus status
        +returnLoan(LocalDate) void
        +checkOverdue(LocalDate) boolean
        +getFine() double
        +getStatus() LoanStatus
        +displayLoan() void
    }

    class BookCategory {
        <<enumeration>>
        FICTION
        NON_FICTION
        TECHNOLOGY
        SCIENCE
    }

    class BookStatus {
        <<enumeration>>
        AVAILABLE
        BORROWED
    }

    class LoanStatus {
        <<enumeration>>
        ACTIVE
        RETURNED
        OVERDUE
    }

    class BookService {
        +addBook(Book) void
        +searchBook(String) Book
        +updateBook(Book) void
        +deleteBook(int) void
    }

    class MemberService {
        +addMember(Member) void
        +searchMember(int) Member
        +updateMember(Member) void
        +deleteMember(int) void
    }

    class LoanService {
        +issueBook(int, int) Loan
        +returnBook(int) void
        +calculateFine(Loan) double
        +getLoanHistory(int) void
    }

    class OverdueMonitor {
        +run() void
        +checkOverdueLoans() void
    }

    class Searchable {
        <<interface>>
        +search(String) Object
    }

    class BookDAO {
        +addBook(Book) void
        +findBook(int) Book
        +updateBook(Book) void
        +deleteBook(int) void
    }

    class MemberDAO {
        +addMember(Member) void
        +findMember(int) Member
        +updateMember(Member) void
        +deleteMember(int) void
    }

    class LoanDAO {
        +createLoan(Loan) void
        +returnLoan(int, LocalDate) void
        +findLoan(int) Loan
        +getLoanHistory(int) List
    }

    class BookRepository
    class MemberRepository
    class LoanRepository

    class FileManager {
        +saveBooks(List) void
        +loadBooks() List
    }

    class LibraryException {
        <<exception>>
    }

    class BookNotFoundException {
        <<exception>>
    }

    class MemberNotFoundException {
        <<exception>>
    }

    Person <|-- Member
    Person <|-- Librarian

    Book --> BookCategory
    Book --> BookStatus
    Loan --> Member
    Loan --> Book
    Loan --> LoanStatus

    BookService --> BookDAO
    MemberService --> MemberDAO
    LoanService --> LoanDAO

    BookService --> BookRepository
    MemberService --> MemberRepository
    LoanService --> LoanRepository

    OverdueMonitor --> LoanService

    BookService ..|> Searchable
    MemberService ..|> Searchable

    BookNotFoundException --|> LibraryException
    MemberNotFoundException --|> LibraryException

    BookService --> Book
    MemberService --> Member
    LoanService --> Loan

    FileManager --> Book
```

## Class Diagram Description

### Person
Abstract base class containing common information such as ID, name, and email.

### Member
Extends `Person` and represents a library member.

### Librarian
Extends `Person` and represents the librarian using the system.

### Book
Stores book information including title, author, ISBN, category, total copies, available copies, and status.

### Loan
Represents a book issued to a member. It maintains issue date, due date, return date, fine, and loan status.

### Service Classes
The service layer contains the main business logic:

- `BookService` manages books.
- `MemberService` manages members.
- `LoanService` manages book issue, return, and fines.
- `OverdueMonitor` checks overdue loans.

### DAO and Repository Classes
These classes provide data-access functionality between the service layer and persistent storage.

### Interfaces and Exceptions
`Searchable` demonstrates interface-based abstraction. Custom exceptions such as `BookNotFoundException` and `MemberNotFoundException` provide meaningful error handling.

### Enumerations
The project uses enumerations to represent:

- `BookCategory`
- `BookStatus`
- `LoanStatus`