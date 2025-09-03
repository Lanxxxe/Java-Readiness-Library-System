import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Main {
    private static final LibrarySystem librarySystem = new LibrarySystem();
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * This test data can be used to accelerate your own testing of your system. It removes the need for you to create
     * your own users and items every time you start the application.
     *
     * NOTE: Just because the application works with this test data, it does not mean it will work with ALL data!
     */
    public static void populateTestData() {
        // Add users
        librarySystem.addUser(new Student("Alice"));
        librarySystem.addUser(new Student("Bob"));
        librarySystem.addUser(new Academic("Dr. Smith"));
        librarySystem.addUser(new ExternalBorrower("Eli"));
        librarySystem.addUser(new ExternalBorrower("Dana"));

        // Add items
        librarySystem.addItem(new Book("Introduction to Algorithms", "9780262033848"));
        librarySystem.addItem(new Book("Clean Code", "9780132350884"));
        librarySystem.addItem(new ReferenceOnlyBook("Oxford English Dictionary", "9780199573158"));
        librarySystem.addItem(new DVD("Inception", 148));
        librarySystem.addItem(new DVD("The Matrix", 136));
        librarySystem.addItem(new Magazine("National Geographic - July 2025", 202507));
        librarySystem.addItem(new Magazine("Popular Science - August 2025", 202508));
    }

    public static void main(String[] args) {
        populateTestData(); // Comment this out if you want to start with no test data.

        boolean running = true;
        System.out.println("=== Library System Console Interface ===");

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. List Users");
            System.out.println("2. List Library Items");
            System.out.println("3. Create User");
            System.out.println("4. Create Library Item");
            System.out.println("5. Loan Item");
            System.out.println("6. Return Item");
            System.out.println("7. Information on Specific User");
            System.out.println("8. Advance Current Date");
            System.out.println("9. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": listUsers(); break;
                case "2": listLibraryItems(); break;
                case "3": createUser(); break;
                case "4": createItem(); break;
                case "5": loanItem(); break;
                case "6": returnItem(); break;
                case "7": userStatus(); break;
                case "8": advanceCurrentDate(); break;
                case "9": running = false; break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
        System.out.println("Exiting Library System. Goodbye!");
    }

    // Handle all the interface control flow logic for creating a new user.
    private static void createUser () {
        System.out.print("\nEnter user name: ");

        String name;
        while (true) {
            name = scanner.nextLine();
            if (name.length() > 0) {
                break;
            }
            System.out.print("\nThe name cannot be empty. Please enter another name: ");
        }

        System.out.println("\nUser Types");
        System.out.println("1. Student");
        System.out.println("2. Academic");
        System.out.println("3. External Borrower");
        System.out.print("Select an option: ");

        String type = scanner.nextLine();
        LibraryUser user = switch (type) {
            case "1" -> new Student(name);
            case "2" -> new Academic(name);
            case "3" -> new ExternalBorrower(name);
            default -> null;
        };

        if (user != null) {
            librarySystem.addUser(user);
            System.out.println("\n" + user.getName() + " has been added to the system.");
        } else {
            System.out.println("Invalid user type.");
        }
    }

    // Handle all the interface control flow logic for creating a new library item.
    private static void createItem () {
        System.out.print("\nEnter item title: ");
        String title = scanner.nextLine();

        System.out.println("\nItem Types");
        System.out.println("1. Book");
        System.out.println("2. DVD");
        System.out.println("3. Magazine");
        System.out.println("3. Reference Only Book");
        System.out.print("Select an option: ");
        String type = scanner.nextLine();

        LibraryItem item = null;
        switch (type) {
            case "1" -> {
                System.out.print("Enter ISBN: ");
                String isbn;
                while (true) {
                    isbn = scanner.nextLine();
                    if (isbn.length() > 0) break;
                    System.out.print("The ISBN cannot be empty. Please enter a valid ISBN: ");
                }
                item = new Book(title, isbn);
            }
            case "2" -> {
                System.out.print("Enter runtime (minutes): ");
                 while (true) {
                    try {
                        int runtime = Integer.parseInt(scanner.nextLine());
                        item = new DVD(title, runtime);
                        break;
                    } catch (Exception e) {
                        System.out.print("The runtime length was invalid. Please enter a valid number: ");
                    }
                }

            }
            case "3" -> {
                System.out.print("Enter issue number: ");
                while (true) {
                    try {
                        int issue = Integer.parseInt(scanner.nextLine());
                        item = new Magazine(title, issue);
                        break;
                    } catch (Exception e) {
                        System.out.print("The issue number was invalid. Please enter a valid issue number: ");
                    }
                }

            }
            case "4" -> {
                System.out.print("Enter ISBN: ");
                String isbn;
                while (true) {
                    isbn = scanner.nextLine();
                    if (isbn.length() > 0) break;
                    System.out.print("The ISBN cannot be empty. Please enter a valid ISBN: ");
                }
                item = new ReferenceOnlyBook(title, isbn);
            }
        }
        librarySystem.addItem(item);
        System.out.println("\n" + item.toString() + " has been added to the library system");
    }

    // Handle all the interface control flow logic for loaning an item.
    private static void loanItem () {

        LibraryUser user = selectUser();
        if (user.isSuspended()) {
            System.out.println("\n" + user.getName() + " is currently suspended and cannot borrow a library item.");
            return;
        }

        List<LibraryItem> availableItems = librarySystem.getBorrowableItems();
        if (availableItems.isEmpty()) {
            System.out.println("\nNo items available for loan. Please check back later.");
            return;
        }

        LibraryItem selectedItem = selectFromList(availableItems, item -> item.toString(), "Items available for loan: ");

        try {
            librarySystem.loanItem(selectedItem, user);
            System.out.println("\n" + selectedItem.getTitle() + " has been loaned to " + user.getName() + ".");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Handle all the interface control flow logic for selecting a user from a list.
    private static LibraryUser selectUser() {
        System.out.println("\nLibrary Users");
        List<LibraryUser> users = librarySystem.getUsers();
        if (users.isEmpty()) {
            System.out.println("No users available.");
            return null;
        }
        for (int i = 0; i < users.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, users.get(i).getName());
        }
        System.out.print("Select user: ");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice < 1 || choice > users.size()) {
            System.out.println("Invalid choice.");
            return null;
        }
        return users.get(choice - 1);
    }

    // Handle all the interface control flow logic for returning a loaned item.
    private static void returnItem () {

        if (librarySystem.getUsers().isEmpty()) {
            System.out.println("\nNo users available.");
            return;
        }
        LibraryUser user = selectFromList(librarySystem.getUsers(), LibraryUser::getName, "Select a user:");

        if (user.getLoans().isEmpty()) {
            System.out.println("\n" + user.getName() + " has no outstanding loans.");
            return;
        }
        Loan loan = selectFromList(user.getLoans(), l -> l.getLibraryItem().getTitle(), "Select a currently loaned item:");

        LibraryItem item = loan.getLibraryItem();
        librarySystem.returnItem(item);

        if (loan.isOverdue())
            System.out.println(user.getName() + " has successfully returned " + item.getTitle() + ", paying a fine of " + loan.getLoanFine() + ".");
        else
            System.out.println(user.getName() + " has successfully returned " + item.getTitle() + ".");
    }

    // Handle all the interface control flow logic for viewing a detailed summary about a specific user.
    private static void userStatus() {
        if (librarySystem.getUsers().isEmpty()) {
            System.out.println("\nNo users available.");
            return;
        }
        LibraryUser user = selectFromList(librarySystem.getUsers(), LibraryUser::getName, "Select a user:");

        System.out.println("\nInformation for " + user.getName() + " (" + user.getUserRole() + ")");

        if (user.getLoans().isEmpty())
            System.out.println("User has no active loans.");
        else {
            System.out.println("This user has the following active loans:");
            for (Loan loan : user.getLoans()) {
                System.out.println(" - " + loan.getLibraryItem().getTitle());
            }
        }

        double fines = user.getTotalFines();
        if (fines == 0) {
            System.out.println("This user has no outstanding fines.");
        } else {
            System.out.println("This user has outstanding fines totalling " + fines + ".");
        }
    }

    // Prints a complete list of all users in the library system to the console.
    private static void listUsers () {
        if (librarySystem.getUsers().isEmpty()) {
            System.out.println("\nThere are no users in the system.");
            return;
        }

        System.out.println("\nLibrary Users:");
        for (LibraryUser user : librarySystem.getUsers()) {
            System.out.println(user.toString());
        }
    }

    // Prints a complete list of all library items, including their current status.
    private static void listLibraryItems () {
        if (librarySystem.getLibraryItems().isEmpty()) {
            System.out.println("\nThere are no library items in the system.");
            return;
        }

        System.out.println("\nLibrary Items:");
        for (LibraryItem item : librarySystem.getLibraryItems()) {
            System.out.println(item.toString());
        }
    }

    /**
     * Helper method to display a list of items and allow the user to select an option.
     * @param items The list of items which is used to populate the UI list.
     * @param displayFunction The method used to convert an item into a String (to build the UI list).
     * @param prompt The header label for the list.
     * @return Returns the item the user has selected. The selection will continue until the user selects a valid item.
     */
    private static <T> T selectFromList(List<T> items, Function<T, String> displayFunction, String prompt) {
        System.out.println("\n");
        if (items.isEmpty()) {
            System.out.println("No items available.");
            return null;
        }

        System.out.println("\n" + prompt);
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, displayFunction.apply(items.get(i)));
        }

        int choice = -1;
        while (true) {
            System.out.print("Select an option: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= items.size()) {
                    return items.get(choice - 1);
                } else {
                    System.out.println("Invalid selection. Please choose between 1 and " + items.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /***
     * Prompts the user to advance the current date in the library simulator forward, checking for appropriate user
     * input and prompting the user to enter a new value if the input given is invalid.
     */
    private static void advanceCurrentDate () {
        System.out.print("\nEnter a number of days to advance: ");
        int days = 0;
        while (true) {
            String input = scanner.nextLine();
            try {
                days = Integer.parseInt(input);
                if (days > 0) {
                    break;
                } else {
                    System.out.print("Please enter a positive number of days: ");
                }
            } catch (Exception e) {
                System.out.print("The input given was invalid. Please enter a single number: ");
            }
        }
        librarySystem.getDateManager().advanceDays(days);
        System.out.println("The library system is now on day " + librarySystem.getDateManager().getCurrentDay() + ".");
    }
}