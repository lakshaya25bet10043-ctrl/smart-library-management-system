# Smart Library Management System - Use Case Diagram

```mermaid
flowchart LR

    L[Librarian]

    subgraph S[Smart Library Management System]
        B1((Add Book))
        B2((Search Book))
        B3((Update Book))
        B4((Delete Book))
        
        M1((Add Member))
        M2((Search Member))
        M3((Update Member))
        M4((Delete Member))
        
        L1((Issue Book))
        L2((Return Book))
        L3((View Loan History))
        
        F1((Calculate Fine))
        O1((Check Overdue Loans))
        
        D1((Store Data))
    end

    L --> B1
    L --> B2
    L --> B3
    L --> B4

    L --> M1
    L --> M2
    L --> M3
    L --> M4

    L --> L1
    L --> L2
    L --> L3

    L --> F1
    L --> O1

    B1 --> D1
    B3 --> D1
    B4 --> D1
    M1 --> D1
    M3 --> D1
    M4 --> D1
    L1 --> D1
    L2 --> D1
```

## Actors

### Librarian

The librarian is the primary actor who interacts with the system to manage books, members, and loans.

## Main Use Cases

### Book Management
- Add books
- Search books
- Update book details
- Delete books
- Manage available copies

### Member Management
- Add members
- Search members
- Update member details
- Delete members

### Loan Management
- Issue books
- Return books
- View loan history

### Fine Management
- Calculate overdue fines
- Check overdue loans

### Data Storage
The system stores library records persistently using the SQLite database.