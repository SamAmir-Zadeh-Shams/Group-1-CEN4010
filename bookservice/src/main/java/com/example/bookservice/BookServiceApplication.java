import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    // ---------- Rating Class ----------
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

    // ---------- Comment Class ----------
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

    // ---------- Storage ----------
    static List<Rating> ratings = new ArrayList<>();
    static List<Comment> comments = new ArrayList<>();


    // ---------- POST /ratings ----------
    public static void createRating(int rating, String userId, String bookId) {

        if (rating < 1 || rating > 5) {
            System.out.println("Error: Rating must be between 1 and 5.");
            return;
        }

        ratings.add(new Rating(rating, userId, bookId));
        System.out.println("POST /ratings → Rating created");
    }


    // ---------- POST /comments ----------
    public static void createComment(String comment, String userId, String bookId) {

        comments.add(new Comment(comment, userId, bookId));
        System.out.println("POST /comments → Comment created");
    }


    // ---------- GET /comments (with sorting) ----------
    public static void getComments(String bookId) {

        System.out.println("\nGET /comments?bookId=" + bookId + " (sorted by newest)");

        comments.stream()
                .filter(c -> c.getBookId().equalsIgnoreCase(bookId))
                .sorted(Comparator.comparing(Comment::getDateStamp).reversed())
                .forEach(c -> System.out.println(
                        c.getComment() + " by " + c.getUserId() + " at " + c.getDateStamp()
                ));
    }


    // ---------- GET /comments?bookId=&userId= (filter by user) ----------
    public static void getCommentsByUser(String bookId, String userId) {

        System.out.println("\nGET /comments?bookId=" + bookId + "&userId=" + userId);

        comments.stream()
                .filter(c -> c.getBookId().equalsIgnoreCase(bookId))
                .filter(c -> c.getUserId().equalsIgnoreCase(userId))
                .sorted(Comparator.comparing(Comment::getDateStamp).reversed())
                .forEach(c -> System.out.println(
                        c.getComment() + " at " + c.getDateStamp()
                ));
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

        if (count == 0) return 0.0;

        return (double) sum / count;
    }


    // ---------- Main (Testing) ----------
    public static void main(String[] args) {

        // Simulated POST requests
        createRating(5, "U1", "B1");
        createRating(4, "U2", "B1");
        createRating(3, "U1", "B1");

        createComment("Great book!", "U1", "B1");
        createComment("Very helpful.", "U2", "B1");
        createComment("Loved it!", "U1", "B1");

        // Simulated GET requests
        getComments("B1");

        getCommentsByUser("B1", "U1");

        double avg = getAverageRating("B1");
        System.out.println("\nGET /ratings/average?bookId=B1");
        System.out.println("Average Rating: " + avg);
    }
}
