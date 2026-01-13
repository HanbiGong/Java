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
package ca.senecacollege.application.workshop2;

public class RecordView {

    private String type;
    private String details;

    public RecordView(String type, String details) {
        this.type = type;
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }
}
