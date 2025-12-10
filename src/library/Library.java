package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Library {
    private final List<Book> books;
    private final List<Member> members;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public boolean addBook(Book book) {
        boolean exists = books.stream().anyMatch(existing -> existing.getId().equals(book.getId()));
        if (exists) {
            return false;
        }
        books.add(book);
        return true;
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }

        System.out.println("Books in library:");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println((i + 1) + ". " + book);
        }
    }

    public Optional<Book> findBookById(String id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public Optional<Member> findMemberById(String id) {
        return members.stream().filter(member -> member.getId().equals(id)).findFirst();
    }

    public List<Book> searchBooksByTitle(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return Collections.emptyList();
        }
        String lower = keyword.toLowerCase();
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public List<Book> searchBooksByAuthor(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return Collections.emptyList();
        }
        String lower = keyword.toLowerCase();
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public boolean borrowBook(String bookId) {
        Optional<Book> bookOpt = findBookById(bookId);
        if (bookOpt.isEmpty()) {
            return false;
        }
        Book book = bookOpt.get();
        if (!book.isAvailable()) {
            return false;
        }
        book.setAvailable(false);
        return true;
    }

    public boolean returnBook(String bookId) {
        Optional<Book> bookOpt = findBookById(bookId);
        if (bookOpt.isEmpty()) {
            return false;
        }
        Book book = bookOpt.get();
        if (book.isAvailable()) {
            return false;
        }
        book.setAvailable(true);
        return true;
    }
}

