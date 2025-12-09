package library;

import java.util.List;
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

            boolean searching = true;
            while (searching) {
                System.out.print("\nSearch by (1) title, (2) author, (q) quit search: ");
                String option = scanner.nextLine().trim().toLowerCase();
                switch (option) {
                    case "1":
                        System.out.print("Enter title keyword: ");
                        String titleKey = scanner.nextLine().trim();
                        List<Book> byTitle = library.searchBooksByTitle(titleKey);
                        printBooks("Results by title", byTitle);
                        break;
                    case "2":
                        System.out.print("Enter author keyword: ");
                        String authorKey = scanner.nextLine().trim();
                        List<Book> byAuthor = library.searchBooksByAuthor(authorKey);
                        printBooks("Results by author", byAuthor);
                        break;
                    case "q":
                        searching = false;
                        break;
                    default:
                        System.out.println("Invalid option. Choose 1, 2, or q.");
                        break;
                }
            }
        }

        System.out.println();
        library.displayAllBooks();
    }

    private static void printBooks(String label, List<Book> books) {
        System.out.println("\n" + label + ":");
        if (books.isEmpty()) {
            System.out.println("No matching books found.");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }
}

