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

/** Concrete smart TV */
public class SmartTV extends EntertainmentDevice {
    public SmartTV(double price) {
        super("SmartTV", price,
                "Streaming and media viewing",
                "Use remote control",
                "Clean screen and update firmware",
                "Visual entertainment");
    }

    @Override
    public void operate() {
        System.out.println(deviceName + " is operated by " + operationMethod);
    }
    @Override
    public void maintain() {
        System.out.println(deviceName + " maintenance: " + maintenanceMethod);
    }
}
