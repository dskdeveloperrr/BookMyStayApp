/**
 * @author Developer
 * @version 7.0
 */

import java.util.*;

/**
 * ============================================================
 * CLASS - AddOnService
 * ============================================================
 *
 * Use Case 7: Add-On Service Selection
 */

class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}


/**
 * ============================================================
 * CLASS - AddOnServiceManager
 * ============================================================
 *
 * Manages mapping between reservation IDs and selected services
 */

class AddOnServiceManager {

    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {

        servicesByReservation
                .computeIfAbsent(reservationId,
                        k -> new ArrayList<>())
                .add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {

        double total = 0;

        List<AddOnService> services =
                servicesByReservation.get(reservationId);

        if (services != null) {

            for (AddOnService service : services) {
                total += service.getCost();
            }
        }

        return total;
    }
}


/**
 * ============================================================
 * MAIN CLASS - UseCase7AddOnServiceSelection
 * ============================================================
 *
 * Demonstrates attaching optional services to reservations
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        AddOnServiceManager manager =
                new AddOnServiceManager();

        String reservationId = "Single-1";

        AddOnService breakfast =
                new AddOnService("Breakfast", 500);

        AddOnService spa =
                new AddOnService("Spa", 1000);

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);

        double totalCost =
                manager.calculateTotalServiceCost(reservationId);

        System.out.println("Reservation ID: "
                + reservationId);

        System.out.println("Total Add-On Cost: "
                + totalCost);
    }
}