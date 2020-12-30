import java.util.Scanner;

public class Assignment {

    private static DataManager dataManager;
    private static Scanner scanner;
    public static void main(String[] args) {

        dataManager = new DataManager();
        System.out.println("Assignment");
        System.out.println("1. Create (without timeout");
        System.out.println("2. Create (with timeout)");
        System.out.println("3. Read");
        System.out.println("4. Delete");
        System.out.print("5. Enter your choice: ");
        scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> createWithoutTimeout();
            case 2 -> createWithTimeout();
            case 3 -> read();
            case 4 -> delete();
        }

    }


    private static void delete() {
        System.out.print("Enter key: ");
        String key = scanner.next();

        dataManager.delete(key);
    }

    private static void read() {
        System.out.print("Enter key: ");
        String key = scanner.next();

        dataManager.read(key);
    }

    private static void createWithTimeout() {
        System.out.print("Enter key: ");
        String key = scanner.next();
        System.out.print("Enter value: ");
        String value = scanner.next();
        System.out.print("Enter timeout (in seconds): ");
        int timeout = scanner.nextInt();

        dataManager.create(key, value, (long) timeout);
    }

    private static void createWithoutTimeout() {
        System.out.print("Enter key: ");
        String key = scanner.next();
        System.out.print("Enter value: ");
        String value = scanner.next();

        dataManager.create(key, value);
    }

}