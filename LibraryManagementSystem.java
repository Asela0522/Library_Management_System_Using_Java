import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Interfaces
interface User {
    void borrowBook(String isbn);

    void returnBook(String isbn);

    void displayBorrowedBooks();

    void displayAvailableBooks();
}

interface Admin {
    void addBook(Book book);

    void removeBook(String isbn);

    void displayAllBooks();
}

// Abstract base class for book categories
abstract class BookCategory {
    private String name;

    public BookCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getCategoryInfo();
}

// Specific category classes
class ChildrenBook extends BookCategory {
    public ChildrenBook(String name) {
        super(name);
    }

    @Override
    public String getCategoryInfo() {
        return "Designed for children.";
    }
}

class Novel extends BookCategory {
    public Novel(String name) {
        super(name);
    }

    @Override
    public String getCategoryInfo() {
        return "Fictional narrative, often in prose.";
    }
}

class AdventureBook extends BookCategory {
    public AdventureBook(String name) {
        super(name);
    }

    @Override
    public String getCategoryInfo() {
        return "Exciting and often dangerous journey.";
    }
}

class HistoryBook extends BookCategory {
    public HistoryBook(String name) {
        super(name);
    }

    @Override
    public String getCategoryInfo() {
        return "Concerned with past events, particularly in human affairs.";
    }
}

// Book class
class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;
    private BookCategory category;

    public Book(String title, String author, String isbn, BookCategory category) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public BookCategory getCategory() {
        return category;
    }
}

// Library class
class Library implements User, Admin {
    private List<Book> books = new ArrayList<>();
    private Map<String, BookCategory> categories = new HashMap<>();
    private List<String> borrowedBooks = new ArrayList<>();

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void removeBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
    }

    @Override
    public void borrowBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && book.isAvailable()) {
                book.setAvailable(false);
                borrowedBooks.add(book.getTitle());
                System.out.println("Book borrowed successfully.");
                return;
            }
        }
        System.out.println("Book not available for borrowing.");
    }

    @Override
    public void returnBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && !book.isAvailable()) {
                book.setAvailable(true);
                borrowedBooks.remove(book.getTitle());
                System.out.println("Book returned successfully.");
                return;
            }
        }
        System.out.println("Book not available for returning.");
    }

    @Override
    public void displayBorrowedBooks() {
        System.out.println("Borrowed Books:");
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books currently borrowed.");
        } else {
            for (String bookTitle : borrowedBooks) {
                System.out.println(bookTitle);
            }
        }
    }

    @Override
    public void displayAvailableBooks() {
        System.out.println("Available Books:");
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println(book.getTitle() + " (ISBN: " + book.getIsbn() + ")");
            }
        }
    }

    @Override
    public void displayAllBooks() {
        System.out.println("All Books:");
        for (Book book : books) {
            System.out.println(book.getTitle() + " by " + book.getAuthor() +
                    " (ISBN: " + book.getIsbn() + ", Category: " + book.getCategory().getName() +
                    ", Info: " + book.getCategory().getCategoryInfo() + ")");
        }
    }

    public void addCategory(BookCategory category) {
        categories.put(category.getName(), category);
    }

    public List<BookCategory> getCategories() {
        return new ArrayList<>(categories.values());
    }
}

// Main class
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to ITUM LIBRARY");

        int roleChoice;
        Library library = new Library();

        // Adding sample categories
        BookCategory childrenBookCategory = new ChildrenBook("Children's Books");
        BookCategory novelCategory = new Novel("Novels");
        BookCategory adventureBookCategory = new AdventureBook("Adventure Books");
        BookCategory historyBookCategory = new HistoryBook("History Books");

        // Adding categories to the library
        library.addCategory(childrenBookCategory);
        library.addCategory(novelCategory);
        library.addCategory(adventureBookCategory);
        library.addCategory(historyBookCategory);

        // Adding sample books to the library with categories
        library.addBook(new Book("The Adventures of Tom Sawyer", "Mark Twain", "123456", childrenBookCategory));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "789012", novelCategory));
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "456789", adventureBookCategory));
        library.addBook(
                new Book("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", "345678", historyBookCategory));

        do {
            // Select role
            System.out.println("\nSelect your role:");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            roleChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (roleChoice == 1) {
                // Admin menu
                while (true) {
                    System.out.println("\nAdmin Menu:");
                    System.out.println("1. Display All Books");
                    System.out.println("2. Add New Book");
                    System.out.println("3. Remove a Book");
                    System.out.println("4. Exit to Role Selection");

                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    switch (choice) {
                        case 1:
                            library.displayAllBooks();
                            break;
                        case 2:
                            System.out.print("Enter book title: ");
                            String newTitle = scanner.nextLine();
                            System.out.print("Enter author: ");
                            String newAuthor = scanner.nextLine();
                            System.out.print("Enter ISBN: ");
                            String newIsbn = scanner.nextLine();

                            System.out.println("Select a category:");
                            int categoryIndex = 1;
                            for (BookCategory category : library.getCategories()) {
                                System.out.println(categoryIndex + ". " + category.getName());
                                categoryIndex++;
                            }

                            System.out.print("Enter category number: ");
                            int selectedCategoryIndex = scanner.nextInt();
                            scanner.nextLine(); // consume newline
                            BookCategory selectedCategory = library.getCategories().get(selectedCategoryIndex - 1);

                            Book newBook = new Book(newTitle, newAuthor, newIsbn, selectedCategory);
                            library.addBook(newBook);
                            System.out.println("New book added successfully.");
                            break;
                        case 3:
                            System.out.print("Enter ISBN of the book you want to remove: ");
                            String removeIsbn = scanner.nextLine();
                            library.removeBook(removeIsbn);
                            System.out.println("Book removed successfully.");
                            break;
                        case 4:
                            System.out.println("Exiting Admin Menu to Role Selection...");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }

                    if (choice == 4) {
                        break;
                    }
                }
            } else if (roleChoice == 2) {
                // User menu
                while (true) {
                    System.out.println("\nUser Menu:");
                    System.out.println("1. Display Borrowed Books");
                    System.out.println("2. Borrow a Book");
                    System.out.println("3. Return a Book");
                    System.out.println("4. Display Available Books");
                    System.out.println("5. Exit to Role Selection");

                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    switch (choice) {
                        case 1:
                            library.displayBorrowedBooks();
                            break;
                        case 2:
                            System.out.print("Enter ISBN of the book you want to borrow: ");
                            String borrowIsbn = scanner.nextLine();
                            library.borrowBook(borrowIsbn);
                            break;
                        case 3:
                            System.out.print("Enter ISBN of the book you want to return: ");
                            String returnIsbn = scanner.nextLine();
                            library.returnBook(returnIsbn);
                            break;
                        case 4:
                            library.displayAvailableBooks();
                            break;
                        case 5:
                            System.out.println("Exiting User Menu to Role Selection...");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }

                    if (choice == 5) {
                        break;
                    }
                }
            } else if (roleChoice != 3) {
                System.out.println("Invalid role choice. Please try again.");
            }
        } while (roleChoice != 3);

        System.out.println("Exiting ITUM LIBRARY...");
    }
}
