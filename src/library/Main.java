package library;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        Book book1 = new Book("B1", "Clean Code", "Robert C. Martin");
        Book book2 = new Book("B2", "Effective Java", "Joshua Bloch");
        Member member1 = new Member("M1", "Alice", "alice@example.com");
        Member member2 = new Member("M2", "Bob", "bob@example.com");

        library.addBook(book1);
        library.addBook(book2);
        library.addMember(member1);
        library.addMember(member2);

        System.out.println("Books:");
        library.getBooks().forEach(System.out::println);

        System.out.println("\nMembers:");
        library.getMembers().forEach(System.out::println);
    }
}

