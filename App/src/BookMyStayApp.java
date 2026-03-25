/**
 * @author Developer
 * @version 6.0
 */

import java.util.*;

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

    public int getAvailability(String type) {

        return availability.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {

        availability.put(type,
                availability.get(type) - 1);
    }
}


/**
 * ============================================================
 * CLASS - RoomAllocationService
 * ============================================================
 */

class RoomAllocationService {

    private Set<String> allocatedRoomIds;

    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {

        allocatedRoomIds = new HashSet<>();

        assignedRoomsByType = new HashMap<>();
    }


    public void allocateRoom(
            Reservation reservation,
            RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {

            System.out.println(
                    "No rooms available for "
                            + reservation.getGuestName());

            return;
        }


        String roomId = generateRoomId(roomType);


        allocatedRoomIds.add(roomId);


        assignedRoomsByType
                .computeIfAbsent(
                        roomType,
                        k -> new HashSet<>())
                .add(roomId);


        inventory.reduceAvailability(roomType);


        System.out.println(
                "Booking confirmed for Guest: "
                        + reservation.getGuestName()
                        + ", Room ID: "
                        + roomId);
    }


    private String generateRoomId(String roomType) {

        int nextNumber =
                assignedRoomsByType
                        .getOrDefault(
                                roomType,
                                new HashSet<>())
                        .size() + 1;

        return roomType + "-" + nextNumber;
    }
}


/**
 * ============================================================
 * MAIN CLASS - BookMyStayApp
 * ============================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");


        RoomInventory inventory = new RoomInventory();


        Queue<Reservation> queue =
                new LinkedList<>();


        queue.offer(new Reservation("Abhi", "Single"));
        queue.offer(new Reservation("Subha", "Single"));
        queue.offer(new Reservation("Vanmathi", "Suite"));


        RoomAllocationService service =
                new RoomAllocationService();


        while (!queue.isEmpty()) {

            service.allocateRoom(
                    queue.poll(),
                    inventory);
        }
    }
}