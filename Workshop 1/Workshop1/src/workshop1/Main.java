/**********************************************
 Workshop #
 Course: APD545 - Fall 2025
 Last Name: Hanbi
 First Name: Gong
 ID: 111932224
 Section: APD545NBB
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: Sep 23th, 2025
 **********************************************/

package workshop1;

import workshop1.model.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Main class for Workshop 1
 * Handles Requirements 1-6 with modularized code
 */
public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // ===== Requirement 1: Read prices =====
        BaseDevice[] devices = new BaseDevice[] {
                new SmartPhone(readPrice(input, "SmartPhone")),
                new Tablet(readPrice(input, "Tablet")),
                new GamingConsole(readPrice(input, "GamingConsole")),
                new SmartTV(readPrice(input, "SmartTV")),
                new SmartSpeaker(readPrice(input, "SmartSpeaker"))
        };

        // ===== Requirement 2: Most expensive device =====
        BaseDevice mostExp = findMostExpensive(devices);
        System.out.println("\n-- Requirement 2 --");
        System.out.println("The most expensive device is: " + mostExp.getDeviceName());
        System.out.println(mostExp.getDeviceName() + "'s cost is: $" + mostExp.getDevicePrice());
        System.out.println(mostExp.getDeviceName() + " is operated: " + mostExp.getOperationMethod());
        System.out.println(mostExp.getDeviceName() + " maintenance: " + mostExp.getMaintenanceMethod());
        System.out.println(mostExp.getDeviceName() + " function type: " + mostExp.getFunctionCategory());

        // ===== Requirement 3: Devices in descending order of price =====
        Arrays.sort(devices); // compareTo() in BaseDevice
        System.out.println("\n-- Requirement 3 --");
        System.out.println("Devices in Descending Order of Price:");
        for (BaseDevice d : devices) {
            System.out.println(d.getDeviceName());
        }

        // ===== Requirement 4: Display functionality by category =====
        input.nextLine(); // consume leftover newline
        System.out.print("\nEnter a device category (CommunicationDevice, EntertainmentDevice, UtilityDevice): ");
        String category = input.nextLine().trim();
        showCategoryFunction(category, devices);

        // ===== Requirement 5/6: Sorted devices & most expensive check =====
        System.out.println("\n-- Requirement 5/6: Devices sorted by price --");
        for (BaseDevice d : devices) {
            System.out.println(d.getDeviceName() + " - $" + d.getDevicePrice());
        }
        BaseDevice mostExpensive = devices[0];
        System.out.println("\nMost expensive device: " + mostExpensive.getDeviceName() + " - $" + mostExpensive.getDevicePrice());

        input.close();
    }

    // ===== Helper Methods =====

    /** Read price input from user safely */
    private static double readPrice(Scanner sc, String deviceName) {
        System.out.print("Enter price for " + deviceName + ": ");
        while (true) {
            String line = sc.nextLine().trim();
            try { return Double.parseDouble(line); }
            catch (NumberFormatException e) { System.out.print("Please enter a numeric value: "); }
        }
    }

    /** Find the most expensive device from array */
    private static BaseDevice findMostExpensive(BaseDevice[] devices) {
        BaseDevice max = devices[0];
        for (BaseDevice d : devices) {
            if (d.getDevicePrice() > max.getDevicePrice()) max = d;
        }
        return max;
    }

    /** Show functionality of devices by category */
    private static void showCategoryFunction(String category, BaseDevice[] devices) {
        System.out.println("\nFunctionality of " + category + ":");
        for (BaseDevice d : devices) {
            if ("CommunicationDevice".equalsIgnoreCase(category) && d instanceof CommunicationDevice)
                System.out.println(d.getDeviceName() + ": " + d.getDeviceFunction());
            else if ("EntertainmentDevice".equalsIgnoreCase(category) && d instanceof EntertainmentDevice)
                System.out.println(d.getDeviceName() + ": " + d.getDeviceFunction());
            else if ("UtilityDevice".equalsIgnoreCase(category) && d instanceof UtilityDevice)
                System.out.println(d.getDeviceName() + ": " + d.getDeviceFunction());
        }
    }
}
