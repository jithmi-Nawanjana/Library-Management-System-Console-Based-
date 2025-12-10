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
        library.displayAllMembers();

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

            boolean addingMembers = true;
            while (addingMembers) {
                System.out.print("\nEnter new member id: ");
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

                System.out.print("Add another member? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!choice.equals("y")) {
                    addingMembers = false;
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

            boolean borrowing = true;
            while (borrowing) {
                System.out.print("\nBorrow/Return menu: (b) borrow, (r) return, (c) check status, (q) quit: ");
                String option = scanner.nextLine().trim().toLowerCase();
                switch (option) {
                    case "b":
                        System.out.print("Enter member id: ");
                        String borrowerId = scanner.nextLine().trim();
                        System.out.print("Enter book id to borrow: ");
                        String borrowId = scanner.nextLine().trim();
                        boolean borrowed = library.borrowBook(borrowId, borrowerId);
                        if (borrowed) {
                            System.out.println("Book borrowed and assigned to member.");
                        } else {
                            System.out.println("Borrow failed (check ids or availability).");
                        }
                        break;
                    case "r":
                        System.out.print("Enter member id: ");
                        String returnMemberId = scanner.nextLine().trim();
                        System.out.print("Enter book id to return: ");
                        String returnId = scanner.nextLine().trim();
                        boolean returned = library.returnBook(returnId, returnMemberId);
                        if (returned) {
                            System.out.println("Book returned and removed from member record.");
                        } else {
                            System.out.println("Return failed (check ids or current borrower).");
                        }
                        break;
                    case "c":
                        System.out.print("Enter book id to check: ");
                        String checkId = scanner.nextLine().trim();
                        library.isBookBorrowed(checkId).ifPresentOrElse(
                                borrowedStatus -> {
                                    if (borrowedStatus) {
                                        System.out.println("Book is currently borrowed.");
                                    } else {
                                        System.out.println("Book is available.");
                                    }
                                },
                                () -> System.out.println("Book id not found.")
                        );
                        break;
                    case "q":
                        borrowing = false;
                        break;
                    default:
                        System.out.println("Invalid option. Choose b, r, c, or q.");
                        break;
                }
            }
        }

        System.out.println();
        library.displayAllBooks();
        library.displayAllMembers();
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

