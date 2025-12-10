package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Member {
    private final String id;
    private String name;
    private String email;
    private final List<Book> borrowedBooks;

    public Member(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(borrowedBooks);
    }

    public void addBorrowedBook(Book book) {
        boolean alreadyBorrowed = borrowedBooks.stream().anyMatch(b -> b.getId().equals(book.getId()));
        if (!alreadyBorrowed) {
            borrowedBooks.add(book);
        }
    }

    public boolean removeBorrowedBook(String bookId) {
        return borrowedBooks.removeIf(book -> book.getId().equals(bookId));
    }

    @Override
    public String toString() {
        return "Member{id='" + id + "', name='" + name + "', email='" + email + "', borrowed=" + borrowedBooks.size() + "}";
    }
}

