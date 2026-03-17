import java.util.Queue;
import java.util.LinkedList;
class Reservation {
    private String guestName;
    private String roomType;
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
    public String getGuestName() {
        return guestName;
    }
    public String getRoomType() {
        return roomType;
    }
}
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;
    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("=== Booking Request Queue Demo ===\n");

        BookingRequestQueue queue = new BookingRequestQueue();

        // Guests submit booking requests
        queue.addRequest(new Reservation("Alice", "Deluxe"));
        queue.addRequest(new Reservation("Bob", "Suite"));
        queue.addRequest(new Reservation("Charlie", "Standard"));
        queue.addRequest(new Reservation("Diana", "Deluxe"));

        System.out.println("Booking requests added to queue.\n");

        // Process requests in FIFO order
        while (queue.hasPendingRequests()) {

            Reservation r = queue.getNextRequest();

            System.out.println(
                    "Processing Request -> Guest: "
                            + r.getGuestName()
                            + ", Room Type: "
                            + r.getRoomType()
            );
        }

        System.out.println("\nAll booking requests processed.");
    }
}