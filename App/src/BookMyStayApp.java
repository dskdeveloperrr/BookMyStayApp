/**
 * @author Developer
 * @version 9.0
 */

import java.util.*;

/**
 * ============================================================
 * CLASS - InvalidBookingException
 * ============================================================
 *
 * Custom exception for invalid booking scenarios
 */

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}


/**
 * ============================================================
 * CLASS - RoomInventory
 * ============================================================
 */

class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {

        availability = new HashMap<>();

        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public boolean isValidRoomType(String roomType) {

        return availability.containsKey(roomType);
    }

    public int getAvailability(String roomType) {

        return availability.getOrDefault(roomType, 0);
    }
}


/**
 * ============================================================
 * CLASS - Reservation
 * ============================================================
 */

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


/**
 * ============================================================
 * CLASS - BookingRequestQueue
 * ============================================================
 */

class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {

        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {

        queue.offer(reservation);
    }
}


/**
 * ============================================================
 * CLASS - ReservationValidator
 * ============================================================
 *
 * Performs booking validation
 */

class ReservationValidator {

    public void validate(
            String guestName,
            String roomType,
            RoomInventory inventory)

            throws InvalidBookingException {


        if (guestName == null || guestName.trim().isEmpty()) {

            throw new InvalidBookingException(
                    "Guest name cannot be empty.");
        }


        if (!inventory.isValidRoomType(roomType)) {

            throw new InvalidBookingException(
                    "Invalid room type selected.");
        }


        if (inventory.getAvailability(roomType) <= 0) {

            throw new InvalidBookingException(
                    "Selected room type is not available.");
        }
    }
}


/**
 * ============================================================
 * MAIN CLASS - UseCase9ErrorHandlingValidation
 * ============================================================
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();

        ReservationValidator validator =
                new ReservationValidator();

        BookingRequestQueue bookingQueue =
                new BookingRequestQueue();

        try {

            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print(
                    "Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();


            validator.validate(
                    guestName,
                    roomType,
                    inventory);


            Reservation reservation =
                    new Reservation(guestName, roomType);

            bookingQueue.addRequest(reservation);


            System.out.println(
                    "Booking request accepted successfully.");

        }

        catch (InvalidBookingException e) {

            System.out.println(
                    "Booking failed: "
                            + e.getMessage());
        }

        finally {

            scanner.close();
        }
    }
}