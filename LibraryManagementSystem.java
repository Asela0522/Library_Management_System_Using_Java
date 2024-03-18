import java.util.ArrayList; // import the ArrayList class
import java.util.HashMap; // import the HashMap class
import java.util.List;  // import the List class
import java.util.Map;   // import the Map class
import java.util.Scanner; //    import the Scanner class

// Interfaces for user and admin   
interface User {// User interface
    void borrowBook(String isbn); // method to borrow a book
    void returnBook(String isbn); // method to return a book
    void displayBorrowedBooks(); // method to display borrowed books
    void displayAvailableBooks(); // method to display available books
}

interface Admin { // Admin interface
    void addBook(Book book); // method to add a book
    void removeBook(String isbn); // method to remove a book
    void displayAllBooks();// method to display all books
}
// Abstract base class for book categories 
abstract class BookCategory { // BookCategory class
    private String name; // name of the category 
    public BookCategory(String name) {
        this.name = name; // set the name of the category 
    }
    public String getName() {
        return name; // return the name of the category
    }

    // Abstract method to be implemented by subclasses
    public abstract String getCategoryInfo(); // method to get category information 
}

// Specific category classes
class ChildrenBook extends BookCategory { // ChildrenBook class extends BookCategory
    public ChildrenBook(String name) {   // constructor
        super(name); // call the constructor of the superclass
    }

    @Override
    public String getCategoryInfo() { // method to get category information
        return "Designed for children."; // return the category information
    }
}
class Novel extends BookCategory {// Novel class extends BookCategory
    public Novel(String name) {// constructor
        super(name);// call the constructor of the superclass
    }

    @Override// method to get category information
    public String getCategoryInfo() {// method to get category information
        return "Fictional narrative, often in prose.";// return the category information
    }
}
class AdventureBook extends BookCategory {// AdventureBook class extends BookCategory
    public AdventureBook(String name) {// constructor 
        super(name);// call the constructor of the superclass
    }

    @Override// method to get category information
    public String getCategoryInfo() {// method to get category information
        return "Exciting and often dangerous journey.";// return the category information
    }
}
class HistoryBook extends BookCategory {// HistoryBook class extends BookCategory
    public HistoryBook(String name) {// constructor
        super(name);// call the constructor of the superclass
    }

    @Override// method to get category information
    public String getCategoryInfo() {// method to get category information
        return "Concerned with past events, particularly in human affairs.";// return the category information
    }
}

// Book class
class Book { // Book class
    private String title;   // title of the book
    private String author;  // author of the book 
    private String isbn;    // ISBN of the book
    private boolean isAvailable;    // availability of the book
    private BookCategory category;  // category of the book

    public Book(String title, String author, String isbn, BookCategory category) {  // constructor
        this.title = title;// set the title of the book
        this.author = author;// set the author of the book
        this.isbn = isbn;   // set the ISBN of the book
        this.isAvailable = true;    // set the availability of the book
        this.category = category;   // set the category of the book
    }

    public String getTitle() {  // method to get the title of the book
        return title;   // return the title of the book
    }

    public String getAuthor() { // method to get the author of the book
        return author;  // return the author of the book
    }

    public String getIsbn() {   // method to get the ISBN of the book
        return isbn;    // return the ISBN of the book
    }

    public boolean isAvailable() {  // method to check the availability of the book
        return isAvailable;     // return the availability of the book
    }

    public void setAvailable(boolean available) {   // method to set the availability of the book
        isAvailable = available;    // set the availability of the book
    }

    public BookCategory getCategory() {     // method to get the category of the book
        return category;    // return the category of the book
    }
}

// Library class
class Library implements User, Admin {  // Library class implements User and Admin interfaces
    private List<Book> books = new ArrayList<>();   // list of books
    private Map<String, BookCategory> categories = new HashMap<>(); // map of categories
    private List<String> borrowedBooks = new ArrayList<>(); // list of borrowed books

    @Override   // method to add a book
    public void addBook(Book book) {    // method to add a book
        books.add(book);    // add the book to the list of books
    }

    @Override   // method to remove a book
    public void removeBook(String isbn) {   // method to remove a book
        books.removeIf(book -> book.getIsbn().equals(isbn));    // remove the book from the list of books
    }

    @Override   // method to borrow a book
    public void borrowBook(String isbn) {   // method to borrow a book
        for (Book book : books) {   // iterate through the list of books
            if (book.getIsbn().equals(isbn) && book.isAvailable()) {    // check if the book is available
                book.setAvailable(false);   // set the book as unavailable
                borrowedBooks.add(book.getTitle()); // add the book to the list of borrowed books
                System.out.println("Book borrowed successfully.");  // print a message
                return; // return from the method
            }
        }
        System.out.println("Book not available for borrowing.");    // print a message
    }

    @Override   // method to return a book
    public void returnBook(String isbn) {   // method to return a book
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
