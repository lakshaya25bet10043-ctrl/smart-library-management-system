# Sequence Diagram – Book Issue Process

## Book Issue Workflow

The sequence diagram describes how a librarian issues a book to a library member.

```text
Librarian
    |
    | 1. Enter Member ID and Book ID
    v
Main Application
    |
    | 2. Validate Member
    v
MemberService
    |
    | 3. Get member details
    v
MemberRepository
    |
    | 4. Return member
    v
MemberService
    |
    | 5. Validate Book
    v
BookService
    |
    | 6. Get book details
    v
BookRepository
    |
    | 7. Return book
    v
BookService
    |
    | 8. Check available copies
    v
LoanService
    |
    | 9. Create loan
    v
LoanRepository
    |
    | 10. Save loan
    v
SQLite Database
    |
    | 11. Loan saved
    v
LoanService
    |
    | 12. Update book availability
    v
BookRepository
    |
    | 13. Update book
    v
SQLite Database
    |
    | 14. Success
    v
Main Application
    |
    | 15. Display loan details
    v
Librarian