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

import ca.senecacollege.application.workshop2.model.UsageLog;
import java.util.*;

// manager for usage log
public class UsageManager {

    // key = vehicle model, value = usage list
    private Map<String, List<UsageLog>> usageMap = new HashMap<>();

    // add log to map
    public void addLog(String model, UsageLog log) {

        if (!usageMap.containsKey(model)) {
            usageMap.put(model, new ArrayList<>());
        }

        usageMap.get(model).add(log);
    }

    // get logs for one vehicle
    public List<UsageLog> getLogs(String model) {
        return usageMap.getOrDefault(model, new ArrayList<>());
    }

    // get all usage
    public Map<String, List<UsageLog>> getAll() {
        return usageMap;
    }
}
