/**
 * @author Developer
 * @version 10.0
 */

import java.util.*;

/**
 * ============================================================
 * CLASS - InvalidBookingException
 * ============================================================
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

    public void increaseAvailability(String roomType) {

        availability.put(roomType,
                availability.get(roomType) + 1);
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
 * CLASS - CancellationService
 * ============================================================
 */

class CancellationService {

    private Stack<String> releasedRoomIds;

    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {

        releasedRoomIds = new Stack<>();

        reservationRoomTypeMap = new HashMap<>();
    }


    public void registerBooking(
            String reservationId,
            String roomType) {

        reservationRoomTypeMap.put(
                reservationId,
                roomType);
    }


    public void cancelBooking(
            String reservationId,
            RoomInventory inventory) {

        if (!reservationRoomTypeMap
                .containsKey(reservationId)) {

            System.out.println(
                    "Invalid cancellation request.");
            return;
        }


        String roomType =
                reservationRoomTypeMap.get(reservationId);


        releasedRoomIds.push(reservationId);


        inventory.increaseAvailability(roomType);


        reservationRoomTypeMap.remove(reservationId);


        System.out.println(
                "Booking cancelled successfully. "
                        + "Inventory restored for room type: "
                        + roomType);
    }


    public void showRollbackHistory() {

        System.out.println(
                "\nRollback History (Most Recent First):");

        while (!releasedRoomIds.isEmpty()) {

            System.out.println(
                    "Released Reservation ID: "
                            + releasedRoomIds.pop());
        }
    }
}


/**
 * ============================================================
 * MAIN CLASS - BookMyStayApp
 * ============================================================
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        RoomInventory inventory =
                new RoomInventory();

        CancellationService cancellationService =
                new CancellationService();

        cancellationService.registerBooking(
                "Single-1",
                "Single");

        cancellationService.cancelBooking(
                "Single-1",
                inventory);

        cancellationService.showRollbackHistory();

        System.out.println(
                "\nUpdated Single Room Availability: "
                        + inventory.getAvailability("Single"));
    }
}