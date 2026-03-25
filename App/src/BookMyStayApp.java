/*
 * @author Developer
 * @version 12.0
 */

import java.io.*;
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

    public Map<String, Integer> getRoomAvailability() {

        return availability;
    }

    public void updateAvailability(String roomType, int count) {

        availability.put(roomType, count);
    }
}


/**
 * ============================================================
 * CLASS - FilePersistenceService
 * ============================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 */

class FilePersistenceService {

    public void saveInventory(
            RoomInventory inventory,
            String filePath) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(filePath))) {

            for (Map.Entry<String, Integer> entry :
                    inventory.getRoomAvailability().entrySet()) {

                writer.write(
                        entry.getKey()
                                + "="
                                + entry.getValue());

                writer.newLine();
            }

            System.out.println(
                    "Inventory saved successfully.");

        }

        catch (IOException e) {

            System.out.println(
                    "Error saving inventory.");
        }
    }


    public void loadInventory(
            RoomInventory inventory,
            String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {

            System.out.println(
                    "No valid inventory data found. Starting fresh.");

            return;
        }


        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("=");

                if (parts.length == 2) {

                    String roomType = parts[0];

                    int count =
                            Integer.parseInt(parts[1]);

                    inventory.updateAvailability(
                            roomType,
                            count);
                }
            }

        }

        catch (IOException | NumberFormatException e) {

            System.out.println(
                    "Error loading inventory data.");
        }
    }
}


/**
 * ============================================================
 * MAIN CLASS - UseCase12DataPersistenceRecovery
 * ============================================================
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("System Recovery");


        String filePath = "inventory.txt";


        RoomInventory inventory =
                new RoomInventory();


        FilePersistenceService persistenceService =
                new FilePersistenceService();


        persistenceService.loadInventory(
                inventory,
                filePath);


        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry :
                inventory.getRoomAvailability().entrySet()) {

            System.out.println(
                    entry.getKey()
                            + ": "
                            + entry.getValue());
        }


        persistenceService.saveInventory(
                inventory,
                filePath);
    }
}