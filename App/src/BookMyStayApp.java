/*
 * @author Developer
 * @version 11.0
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
 * CLASS - BookingRequestQueue
 * ============================================================
 */

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

    public int getAvailability(String roomType) {

        return availability.get(roomType);
    }

    public void decreaseAvailability(String roomType) {

        availability.put(
                roomType,
                availability.get(roomType) - 1
        );
    }
}


/**
 * ============================================================
 * CLASS - RoomAllocationService
 * ============================================================
 */

class RoomAllocationService {

    private Map<String, Integer> roomCounters;

    public RoomAllocationService() {

        roomCounters = new HashMap<>();

        roomCounters.put("Single", 1);
        roomCounters.put("Double", 1);
        roomCounters.put("Suite", 1);
    }

    public void allocateRoom(
            Reservation reservation,
            RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {

            System.out.println(
                    "No rooms available for "
                            + reservation.getGuestName()
            );

            return;
        }

        int counter = roomCounters.get(roomType);

        String roomId = roomType + "-" + counter;

        roomCounters.put(roomType, counter + 1);

        inventory.decreaseAvailability(roomType);

        System.out.println(
                "Booking confirmed for Guest: "
                        + reservation.getGuestName()
                        + ", Room ID: "
                        + roomId
        );
    }
}


/**
 * ============================================================
 * CLASS - ConcurrentBookingProcessor
 * ============================================================
 */

class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;

    private RoomInventory inventory;

    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }


    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            synchronized (bookingQueue) {

                if (!bookingQueue.hasPendingRequests()) {

                    return;
                }

                reservation =
                        bookingQueue.getNextRequest();
            }


            synchronized (inventory) {

                allocationService.allocateRoom(
                        reservation,
                        inventory
                );
            }
        }
    }
}


/**
 * ============================================================
 * MAIN CLASS - UseCase11ConcurrentBookingSimulation
 * ============================================================
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println(
                "Concurrent Booking Simulation\n"
        );


        BookingRequestQueue bookingQueue =
                new BookingRequestQueue();

        RoomInventory inventory =
                new RoomInventory();

        RoomAllocationService allocationService =
                new RoomAllocationService();


        bookingQueue.addRequest(
                new Reservation("Abhi", "Single"));

        bookingQueue.addRequest(
                new Reservation("Vanmathi", "Double"));

        bookingQueue.addRequest(
                new Reservation("Kural", "Suite"));

        bookingQueue.addRequest(
                new Reservation("Subha", "Single"));


        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue,
                        inventory,
                        allocationService));

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue,
                        inventory,
                        allocationService));


        t1.start();
        t2.start();


        try {

            t1.join();
            t2.join();

        }

        catch (InterruptedException e) {

            System.out.println(
                    "Thread execution interrupted."
            );
        }


        System.out.println(
                "\nRemaining Inventory:"
        );

        System.out.println(
                "Single: "
                        + inventory.getAvailability("Single"));

        System.out.println(
                "Double: "
                        + inventory.getAvailability("Double"));

        System.out.println(
                "Suite: "
                        + inventory.getAvailability("Suite"));
    }
}