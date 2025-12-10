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

        runMenu(library);
    }

    private static void runMenu(Library library) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        handleAddBook(library, scanner);
                        break;
                    case "2":
                        handleSearchBook(library, scanner);
                        break;
                    case "3":
                        handleBorrowBook(library, scanner);
                        break;
                    case "4":
                        handleReturnBook(library, scanner);
                        break;
                    case "5":
                        handleAddMember(library, scanner);
                        break;
                    case "6":
                        library.displayAllBooks();
                        break;
                    case "7":
                        running = false;
                        System.out.println("Exiting. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose 1-7.");
                        break;
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== Library Menu =====");
        System.out.println("1. Add Book");
        System.out.println("2. Search Book");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Add Member");
        System.out.println("6. View All Books");
        System.out.println("7. Exit");
    }

    private static void handleAddBook(Library library, Scanner scanner) {
        System.out.print("Enter new book id: ");
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
    }

    private static void handleSearchBook(Library library, Scanner scanner) {
        System.out.print("Search by (1) title, (2) author: ");
        String option = scanner.nextLine().trim();
        switch (option) {
            case "1":
                System.out.print("Enter title keyword: ");
                String titleKey = scanner.nextLine().trim();
                printBooks("Results by title", library.searchBooksByTitle(titleKey));
                break;
            case "2":
                System.out.print("Enter author keyword: ");
                String authorKey = scanner.nextLine().trim();
                printBooks("Results by author", library.searchBooksByAuthor(authorKey));
                break;
            default:
                System.out.println("Invalid option. Choose 1 or 2.");
                break;
        }
    }

    private static void handleBorrowBook(Library library, Scanner scanner) {
        System.out.print("Enter member id: ");
        String memberId = scanner.nextLine().trim();
        System.out.print("Enter book id to borrow: ");
        String bookId = scanner.nextLine().trim();

        boolean borrowed = library.borrowBook(bookId, memberId);
        if (borrowed) {
            System.out.println("Book borrowed and assigned to member.");
        } else {
            System.out.println("Borrow failed (check ids, availability, or borrow limit).");
        }
    }

    private static void handleReturnBook(Library library, Scanner scanner) {
        System.out.print("Enter member id: ");
        String memberId = scanner.nextLine().trim();
        System.out.print("Enter book id to return: ");
        String bookId = scanner.nextLine().trim();

        boolean returned = library.returnBook(bookId, memberId);
        if (returned) {
            System.out.println("Book returned and removed from member record.");
        } else {
            System.out.println("Return failed (check ids or current borrower).");
        }
    }

    private static void handleAddMember(Library library, Scanner scanner) {
        System.out.print("Enter new member id: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Enter member name: ");
        String memberName = scanner.nextLine().trim();

        System.out.print("Enter member email: ");
        String memberEmail = scanner.nextLine().trim();

        boolean memberAdded = library.addMember(new Member(memberId, memberName, memberEmail));
        if (memberAdded) {
            System.out.println("Member added!");
        } else {
            System.out.println("Member id already exists. Try another id.");
        }
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

