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

package workshop1.model;

/**
 * Abstract parent for utility devices.
 */
public abstract class UtilityDevice extends BaseDevice {
    public UtilityDevice(String name, double price, String function,
                         String operation, String maintenance, String category) {
        super(name, price, function, operation, maintenance, category);
    }
}
