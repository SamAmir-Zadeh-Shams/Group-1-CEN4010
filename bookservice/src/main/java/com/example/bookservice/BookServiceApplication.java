import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // ================= Rating Class =================
    static class Rating {
        private int rating;
        private String userId;
        private String bookId;
        private LocalDateTime dateStamp;

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

    // ================= Comment Class =================
    static class Comment {
        private String comment;
        private String userId;
        private String bookId;
        private LocalDateTime dateStamp;

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

    // ================= Storage =================
    static List<Rating> ratings = new ArrayList<>();
    static List<Comment> comments = new ArrayList<>();

    // ================= Create Rating =================
    public static void createRating(int rating, String userId, String bookId) {

        if (rating < 1 || rating > 5) {
            System.out.println("Rating must be between 1 and 5.");
            return;
        }

        ratings.add(new Rating(rating, userId, bookId));
        System.out.println("Rating added successfully.");
    }

    // ================= Create Comment =================
    public static void createComment(String comment, String userId, String bookId) {

        comments.add(new Comment(comment, userId, bookId));
        System.out.println("Comment added successfully.");
    }

    // ================= Retrieve Comments =================
    public static void getComments(String bookId) {

        System.out.println("\nComments for Book " + bookId + ":");

        for (Comment c : comments) {
            if (c.getBookId().equalsIgnoreCase(bookId)) {
                System.out.println(c.getComment() + " by " + c.getUserId() + " at " + c.getDateStamp());
            }
        }
    }

    // ================= Average Rating =================
    public static double getAverageRating(String bookId) {

        int sum = 0;
        int count = 0;

        for (Rating r : ratings) {
            if (r.getBookId().equalsIgnoreCase(bookId)) {
                sum += r.getRating();
                count++;
            }
        }

        if (count == 0) return 0.0;

        return (double) sum / count;
    }

    // ================= Main Menu =================
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n--- Book Service Menu ---");
            System.out.println("1. Add Rating");
            System.out.println("2. Add Comment");
            System.out.println("3. View Comments");
            System.out.println("4. View Average Rating");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {

                System.out.print("User ID: ");
                String user = scanner.nextLine();

                System.out.print("Book ID: ");
                String book = scanner.nextLine();

                System.out.print("Rating (1-5): ");
                int rating = scanner.nextInt();

                createRating(rating, user, book);
            }

            else if (choice == 2) {

                System.out.print("User ID: ");
                String user = scanner.nextLine();

                System.out.print("Book ID: ");
                String book = scanner.nextLine();

                System.out.print("Comment: ");
                String comment = scanner.nextLine();

                createComment(comment, user, book);
            }

            else if (choice == 3) {

                System.out.print("Book ID: ");
                String book = scanner.nextLine();

                getComments(book);
            }

            else if (choice == 4) {

                System.out.print("Book ID: ");
                String book = scanner.nextLine();

                double avg = getAverageRating(book);
                System.out.println("Average Rating: " + avg);
            }

            else if (choice == 5) {
                break;
            }
        }

        scanner.close();
    }
}
