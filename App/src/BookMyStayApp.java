/**
 * @author Developer
 * @version 10.0
 */

import java.util.*;

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

    public void increaseAvailability(String roomType) {

        availability.put(roomType,
                availability.get(roomType) + 1);
    }

    public int getAvailability(String roomType) {

        return availability.get(roomType);
    }
}


/**
 * ============================================================
 * CLASS - CancellationService
 * ============================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
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
 * MAIN CLASS - UseCase10BookingCancellation
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