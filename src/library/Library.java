package library;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public boolean saveBooksToFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (Book book : books) {
                writer.printf("%s,%s,%s,%b%n",
                        book.getId(),
                        escapeComma(book.getTitle()),
                        escapeComma(book.getAuthor()),
                        book.isAvailable());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Failed to save books: " + e.getMessage());
            return false;
        }
    }

    public boolean loadBooksFromFile(String filePath) {
        List<Book> loaded = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = splitCsvLine(line);
                if (parts.length != 4) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                String id = parts[0];
                String title = unescapeComma(parts[1]);
                String author = unescapeComma(parts[2]);
                boolean available = Boolean.parseBoolean(parts[3]);
                Book book = new Book(id, title, author);
                book.setAvailable(available);
                loaded.add(book);
            }
        } catch (IOException e) {
            System.out.println("Failed to load books: " + e.getMessage());
            return false;
        }

        // Replace current list with loaded data, ignoring duplicate ids in the file.
        this.books.clear();
        for (Book book : loaded) {
            addBook(book);
        }
        return true;
    }

    private String escapeComma(String value) {
        return value.replace(",", "\\,");
    }

    private String unescapeComma(String value) {
        return value.replace("\\,", ",");
    }

    private String[] splitCsvLine(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean escaping = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (escaping) {
                current.append(c);
                escaping = false;
            } else if (c == '\\') {
                escaping = true;
            } else if (c == ',') {
                parts.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        parts.add(current.toString());
        return parts.toArray(new String[0]);
    }
}

