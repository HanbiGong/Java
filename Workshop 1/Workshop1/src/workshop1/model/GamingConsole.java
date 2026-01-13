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

/** Concrete gaming console */
public class GamingConsole extends EntertainmentDevice {
    public GamingConsole(double price) {
        super("GamingConsole", price,
                "Video gaming",
                "Use game controllers",
                "Clean vents and update system",
                "Interactive entertainment");
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
