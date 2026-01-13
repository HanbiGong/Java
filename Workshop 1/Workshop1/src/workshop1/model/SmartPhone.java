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

/** Concrete smartphone */
public class SmartPhone extends CommunicationDevice {
    public SmartPhone(double price) {
        super("SmartPhone", price,
                "Communication and apps",
                "Use touchscreen",
                "Regular software updates",
                "Multi-functional");
    }

    @Override
    public void operate() {
        // use protected fields inside same package
        System.out.println(deviceName + " is operated by " + operationMethod);
    }

    @Override
    public void maintain() {
        System.out.println(deviceName + " maintenance: " + maintenanceMethod);
    }
}
