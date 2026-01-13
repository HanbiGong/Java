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

/** Concrete smart speaker */
public class SmartSpeaker extends UtilityDevice {
    public SmartSpeaker(double price) {
        super("SmartSpeaker", price,
                "Voice-controlled assistance",
                "Use voice commands",
                "No maintenance needed",
                "Audio assistance");
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
