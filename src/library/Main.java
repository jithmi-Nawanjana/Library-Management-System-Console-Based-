package library;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        // Seed with a couple of examples.
        library.addBook(new Book("B1", "Clean Code", "Robert C. Martin"));
        library.addBook(new Book("B2", "Effective Java", "Joshua Bloch"));
        library.addMember(new Member("M1", "Alice", "alice@example.com"));
        library.addMember(new Member("M2", "Bob", "bob@example.com"));

        library.displayAllBooks();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean adding = true;
            while (adding) {
                System.out.print("\nEnter new book id: ");
                String id = scanner.nextLine().trim();

                System.out.print("Enter book title: ");
                String title = scanner.nextLine().trim();

                System.out.print("Enter book author: ");
                String author = scanner.nextLine().trim();

                boolean added = library.addBook(new Book(id, title, author));
                if (added) {
                    System.out.println("Book added!");
                } else {
                    System.out.println("Book id already exists. Try another id.");
                }

                System.out.print("Add another book? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!choice.equals("y")) {
                    adding = false;
                }
            }
        }

        System.out.println();
        library.displayAllBooks();
    }
}

