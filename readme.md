# Library Management System (Console-Based)

## Features
- Add/search books; view all books.
- Borrow/return books, track availability, and enforce per-member borrow limits.
- Add members and keep their borrowed book list.
- Save books to a CSV-like file and load them back (commas escaped with `\`).
- Menu-driven console flow using `Scanner` and switch-case.

## Build & Run
From the project root:
```
mkdir -p out
javac -d out src/library/*.java
java -cp out library.Main
```

## Menu Options
1. Add Book  
2. Search Book (by title or author)  
3. Borrow Book (requires member id and book id)  
4. Return Book (requires member id and book id)  
5. Add Member  
6. View All Books  
7. Save Books to File  
8. Load Books from File  
9. Exit  

## Saving/Loading Books
- Save writes lines as: `id,title,author,available` with commas inside fields escaped as `\,`.
- Load replaces the in-memory book list with the file contents, skipping malformed lines.  
  If you want to preserve existing books, save first, then reload after app restart, or adjust logic as needed.

## Notes
- Default borrow limit per member is 3; change via `library.setBorrowLimit(...)` if desired.
- Member records hold the list of borrowed books for future extensions (e.g., per-member listing).
