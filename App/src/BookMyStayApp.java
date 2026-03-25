/**
 * @author Developer
 * @version 8.0
 */

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * CLASS - Reservation
 * ============================================================
 *
 * Represents a confirmed reservation.
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
 * CLASS - BookingHistory
 * ============================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * Maintains ordered storage of confirmed reservations.
 */

class BookingHistory {

    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}


/**
 * ============================================================
 * CLASS - BookingReportService
 * ============================================================
 *
 * Generates summary report from booking history.
 */

class BookingReportService {

    public void generateReport(BookingHistory history) {

        System.out.println("Booking History Report");

        for (Reservation reservation :
                history.getConfirmedReservations()) {

            System.out.println(
                    "Guest: "
                            + reservation.getGuestName()
                            + ", Room Type: "
                            + reservation.getRoomType());
        }
    }
}


/**
 * ============================================================
 * MAIN CLASS - UseCase8BookingHistoryReport
 * ============================================================
 *
 * Demonstrates storing and reporting booking history.
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking History and Reporting\n");

        BookingHistory history = new BookingHistory();

        history.addReservation(
                new Reservation("Abhi", "Single"));

        history.addReservation(
                new Reservation("Subha", "Double"));

        history.addReservation(
                new Reservation("Vanmathi", "Suite"));

        BookingReportService reportService =
                new BookingReportService();

        reportService.generateReport(history);
    }
}
