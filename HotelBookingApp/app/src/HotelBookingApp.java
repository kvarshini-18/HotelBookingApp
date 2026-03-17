import java.io.*;
import java.util.*;
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

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
class RoomInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}
class BookingHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
class PersistenceService {

    private static final String FILE_NAME = "booking_data.ser";

    public void save(BookingHistory history, RoomInventory inventory) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(history);
            out.writeObject(inventory);

            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    public Object[] load() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            BookingHistory history = (BookingHistory) in.readObject();
            RoomInventory inventory = (RoomInventory) in.readObject();

            System.out.println("System state restored successfully.");

            return new Object[]{history, inventory};

        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        return null;
    }
}
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Data Persistence & System Recovery");

        PersistenceService persistence = new PersistenceService();

        BookingHistory history;
        RoomInventory inventory;

        Object[] data = persistence.load();

        if (data != null) {
            history = (BookingHistory) data[0];
            inventory = (RoomInventory) data[1];
        } else {
            history = new BookingHistory();
            inventory = new RoomInventory();

            history.addReservation(new Reservation("Abhi", "Single"));
            history.addReservation(new Reservation("Subha", "Double"));
        }

        System.out.println("\nBooking History:");
        for (Reservation r : history.getReservations()) {
            System.out.println("Guest: " + r.getGuestName()
                    + ", Room Type: " + r.getRoomType());
        }

        System.out.println("\nInventory Snapshot:");
        for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        persistence.save(history, inventory);
    }
}