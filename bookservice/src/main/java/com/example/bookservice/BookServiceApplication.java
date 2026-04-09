import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    // ---------- Rating Class ----------
    static class Rating {
        private final int rating;
        private final String userId;
        private final String bookId;
        private final LocalDateTime dateStamp;

        public Rating(int rating, String userId, String bookId) {
            this.rating = rating;
            this.userId = userId;
            this.bookId = bookId;
            this.dateStamp = LocalDateTime.now();
        }

        public int getRating() { return rating; }
        public String getUserId() { return userId; }
        public String getBookId() { return bookId; }
        public LocalDateTime getDateStamp() { return dateStamp; }
    }

    // ---------- Comment Class ----------
    static class Comment {
        private final String comment;
        private final String userId;
        private final String bookId;
        private final LocalDateTime dateStamp;

        public Comment(String comment, String userId, String bookId) {
            this.comment = comment;
            this.userId = userId;
            this.bookId = bookId;
            this.dateStamp = LocalDateTime.now();
        }

        public String getComment() { return comment; }
        public String getUserId() { return userId; }
        public String getBookId() { return bookId; }
        public LocalDateTime getDateStamp() { return dateStamp; }
    }

    // ---------- Storage ----------
    static List<Rating> ratings = new ArrayList<>();
    static List<Comment> comments = new ArrayList<>();

    // ---------- POST /ratings ----------
    public static void createRating(int rating, String userId, String bookId) {
        if (userId.isEmpty() || bookId.isEmpty()) {
            System.out.println("Error: User ID and Book ID cannot be empty.");
            return;
        }

        if (rating < 1 || rating > 5) {
            System.out.println("Error: Rating must be between 1 and 5.");
            return;
        }

        ratings.add(new Rating(rating, userId, bookId));
        System.out.println("PASS: Rating created successfully.");
    }

    // ---------- POST /comments ----------
    public static void createComment(String comment, String userId, String bookId) {
        if (comment.isEmpty() || userId.isEmpty() || bookId.isEmpty()) {
            System.out.println("Error: Comment, User ID, and Book ID cannot be empty.");
            return;
        }

        comments.add(new Comment(comment, userId, bookId));
        System.out.println("PASS: Comment created successfully.");
    }

    // ---------- GET /comments ----------
    public static void getComments(String bookId) {
        System.out.println("\nGET /comments?bookId=" + bookId);

        boolean found = false;

        comments.stream()
                .filter(c -> c.getBookId().equalsIgnoreCase(bookId))
                .sorted(Comparator.comparing(Comment::getDateStamp).reversed())
                .forEach(c -> {
                    System.out.println(c.getComment() + " by " + c.getUserId() + " at " + c.getDateStamp());
                });

        for (Comment c : comments) {
            if (c.getBookId().equalsIgnoreCase(bookId)) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No comments found for this book.");
        }
    }

    // ---------- GET /comments?bookId=&userId= ----------
    public static void getCommentsByUser(String bookId, String userId) {
        System.out.println("\nGET /comments?bookId=" + bookId + "&userId=" + userId);

        boolean found = false;

        comments.stream()
                .filter(c -> c.getBookId().equalsIgnoreCase(bookId))
                .filter(c -> c.getUserId().equalsIgnoreCase(userId))
                .sorted(Comparator.comparing(Comment::getDateStamp).reversed())
                .forEach(c -> {
                    System.out.println(c.getComment() + " at " + c.getDateStamp());
                });

        for (Comment c : comments) {
            if (c.getBookId().equalsIgnoreCase(bookId) &&
                c.getUserId().equalsIgnoreCase(userId)) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No comments found for this user and book.");
        }
    }

    // ---------- GET /ratings/average ----------
    public static double getAverageRating(String bookId) {
        int sum = 0;
        int count = 0;

        for (Rating r : ratings) {
            if (r.getBookId().equalsIgnoreCase(bookId)) {
                sum += r.getRating();
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No ratings found for this book.");
            return 0.0;
        }

        return (double) sum / count;
    }

    // ---------- INTERACTIVE MAIN ----------
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== BOOK SYSTEM ===");

        while (running) {
            System.out.println("\n1. Add Rating");
            System.out.println("2. Add Comment");
            System.out.println("3. View Comments (Book)");
            System.out.println("4. View Comments (Book + User)");
            System.out.println("5. Get Average Rating");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Rating (1-5): ");
                    int rating = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("User ID: ");
                    String userId = scanner.nextLine();

                    System.out.print("Book ID: ");
                    String bookId = scanner.nextLine();

                    createRating(rating, userId, bookId);
                    break;

                case 2:
                    System.out.print("Comment: ");
                    String comment = scanner.nextLine();

                    System.out.print("User ID: ");
                    userId = scanner.nextLine();

                    System.out.print("Book ID: ");
                    bookId = scanner.nextLine();

                    createComment(comment, userId, bookId);
                    break;

                case 3:
                    System.out.print("Book ID: ");
                    bookId = scanner.nextLine();
                    getComments(bookId);
                    break;

                case 4:
                    System.out.print("Book ID: ");
                    bookId = scanner.nextLine();

                    System.out.print("User ID: ");
                    userId = scanner.nextLine();

                    getCommentsByUser(bookId, userId);
                    break;

                case 5:
                    System.out.print("Book ID: ");
                    bookId = scanner.nextLine();

                    double avg = getAverageRating(bookId);
                    System.out.println("Average Rating: " + avg);
                    break;

                case 6:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}
