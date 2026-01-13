/**********************************************
 Workshop # 2
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-11-24
 **********************************************/
package ca.senecacollege.application.workshop2.manager;

import ca.senecacollege.application.workshop2.model.MaintenanceRecord;
import java.util.*;

// manager for maintenance records
public class MaintenanceManager {

    // key = vehicle model, value = list of records
    private Map<String, List<MaintenanceRecord>> records = new HashMap<>();

    // add new maintenance record
    public void addRecord(String model, MaintenanceRecord record) {

        // if model not found, create new list
        if (!records.containsKey(model)) {
            records.put(model, new ArrayList<>());
        }

        records.get(model).add(record);
    }

    // get all records for a vehicle
    public List<MaintenanceRecord> getRecords(String model) {
        return records.getOrDefault(model, new ArrayList<>());
    }

    // get whole map (for summary)
    public Map<String, List<MaintenanceRecord>> getAll() {
        return records;
    }
}
