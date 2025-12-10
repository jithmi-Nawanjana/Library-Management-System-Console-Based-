package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Library {
    private final List<Book> books;
    private final List<Member> members;
    private int borrowLimit;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.borrowLimit = 3; // default limit per member
    }

    public boolean addBook(Book book) {
        boolean exists = books.stream().anyMatch(existing -> existing.getId().equals(book.getId()));
        if (exists) {
            return false;
        }
        books.add(book);
        return true;
    }

    public boolean addMember(Member member) {
        boolean exists = members.stream().anyMatch(existing -> existing.getId().equals(member.getId()));
        if (exists) {
            return false;
        }
        members.add(member);
        return true;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public int getBorrowLimit() {
        return borrowLimit;
    }

    public void setBorrowLimit(int borrowLimit) {
        if (borrowLimit < 1) {
            throw new IllegalArgumentException("Borrow limit must be at least 1");
        }
        this.borrowLimit = borrowLimit;
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

    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members in the system.");
            return;
        }

        System.out.println("Members:");
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            System.out.println((i + 1) + ". " + member);
        }
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

    public boolean borrowBook(String bookId, String memberId) {
        Optional<Book> bookOpt = findBookById(bookId);
        Optional<Member> memberOpt = findMemberById(memberId);

        if (bookOpt.isEmpty() || memberOpt.isEmpty()) {
            return false;
        }

        Book book = bookOpt.get();
        if (!book.isAvailable()) {
            return false;
        }

        Member member = memberOpt.get();
        if (member.getBorrowedBooks().size() >= borrowLimit) {
            return false;
        }
        book.setAvailable(false);
        member.addBorrowedBook(book);
        return true;
    }

    public boolean returnBook(String bookId, String memberId) {
        Optional<Book> bookOpt = findBookById(bookId);
        Optional<Member> memberOpt = findMemberById(memberId);

        if (bookOpt.isEmpty() || memberOpt.isEmpty()) {
            return false;
        }

        Book book = bookOpt.get();
        Member member = memberOpt.get();

        boolean wasBorrowedByMember = member.removeBorrowedBook(bookId);
        if (!wasBorrowedByMember) {
            return false;
        }

        if (book.isAvailable()) {
            return false;
        }

        book.setAvailable(true);
        return true;
    }

    public Optional<Boolean> isBookBorrowed(String bookId) {
        return findBookById(bookId).map(book -> !book.isAvailable());
    }
}

