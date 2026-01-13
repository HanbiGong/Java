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
 * Abstract base class for devices
 * Implements Comparable to compare prices
 */
public abstract class BaseDevice implements IDeviceOperable, IDeviceMaintanable, Comparable<BaseDevice> {
    protected String deviceName;
    protected double devicePrice;
    protected String deviceFunction;
    protected String operationMethod;
    protected String maintenanceMethod;
    protected String functionCategory;

    public BaseDevice(String name, double price, String function,
                      String operation, String maintenance, String category) {
        this.deviceName = name;
        this.devicePrice = price;
        this.deviceFunction = function;
        this.operationMethod = operation;
        this.maintenanceMethod = maintenance;
        this.functionCategory = category;
    }

    // Getters
    public String getDeviceName() { return deviceName; }
    public double getDevicePrice() { return devicePrice; }
    public String getDeviceFunction() { return deviceFunction; }
    public String getOperationMethod() { return operationMethod; }
    public String getMaintenanceMethod() { return maintenanceMethod; }
    public String getFunctionCategory() { return functionCategory; }

    // Implement compareTo for price-based sorting
    @Override
    public int compareTo(BaseDevice other) {
        return Double.compare(other.devicePrice, this.devicePrice); // 내림차순
    }

    @Override
    public String toString() {
        return deviceName;
    }
}
