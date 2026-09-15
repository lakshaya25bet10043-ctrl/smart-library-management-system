# Testing Documentation

## 1. Testing Overview

The Smart Library Management System was tested to verify the correctness of its main library operations, database interactions, exception handling, file handling, and multithreading features.

Testing was performed by compiling and executing the application and checking the resulting outputs and database changes.

---

## 2. Functional Testing

| Test Case | Operation | Expected Result | Status |
|---|---|---|---|
| TC01 | Add a book | Book is stored successfully | Passed |
| TC02 | Search for a book | Correct book details are displayed | Passed |
| TC03 | Update a book | Book information is updated | Passed |
| TC04 | Delete a book | Book is removed successfully | Passed |
| TC05 | Add a member | Member is stored successfully | Passed |
| TC06 | Search for a member | Correct member details are displayed | Passed |
| TC07 | Update a member | Member information is updated | Passed |
| TC08 | Delete a member | Member is removed successfully | Passed |
| TC09 | Issue a book | Loan record is created and available copies decrease | Passed |
| TC10 | Return a book | Loan is marked returned and book availability increases | Passed |
| TC11 | Calculate overdue fine | Correct fine is calculated | Passed |
| TC12 | View loan history | Loan records are displayed | Passed |

---

## 3. Exception Testing

The application was tested for invalid operations and missing records.

| Test Case | Condition | Expected Result | Status |
|---|---|---|---|
| ET01 | Search for non-existing book | BookNotFoundException is handled | Passed |
| ET02 | Search for non-existing member | MemberNotFoundException is handled | Passed |
| ET03 | Issue book with no available copies | Appropriate error is displayed | Passed |
| ET04 | Return an already returned loan | Error is handled correctly | Passed |

---

## 4. Database Testing

JDBC and SQLite operations were tested for:

- Book insertion
- Book searching
- Book updating
- Book deletion
- Member insertion
- Member searching
- Member updating
- Member deletion
- Loan creation
- Loan return
- Loan history retrieval

The database operations were verified using the SQLite database stored in `data/library.db`.

---

## 5. Fine Calculation Testing

The overdue fine calculation was tested using different return dates.

Example:

```text
Due Date: 21 September 2026
Return Date: 24 September 2026
Overdue Days: 3
Fine Rate: ₹5 per day
Calculated Fine: ₹15