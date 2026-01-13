/**********************************************
 Workshop # 6 & 7
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-11-28
 **********************************************/
package com.seneca.inventory.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Helper class for SQLite database.
 */
public class DbUtil {

    // SQLite file name
    private static final String DB_URL = "jdbc:sqlite:inventory.db";

    // Get a new connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Create tables if they do not exist
    public static void initSchema() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Parts table
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS parts (" +
                            "id INTEGER PRIMARY KEY, " +
                            "name TEXT NOT NULL, " +
                            "price REAL NOT NULL, " +
                            "stock INTEGER NOT NULL, " +
                            "min INTEGER NOT NULL, " +
                            "max INTEGER NOT NULL, " +
                            "type TEXT NOT NULL, " +           // IN_HOUSE or OUTSOURCED
                            "machine_id INTEGER, " +           // for IN_HOUSE
                            "company_name TEXT" +              // for OUTSOURCED
                            ")"
            );

            // Products table
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS products (" +
                            "id INTEGER PRIMARY KEY, " +
                            "name TEXT NOT NULL, " +
                            "price REAL NOT NULL, " +
                            "stock INTEGER NOT NULL, " +
                            "min INTEGER NOT NULL, " +
                            "max INTEGER NOT NULL" +
                            ")"
            );

            // Link table between products and parts
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS product_parts (" +
                            "product_id INTEGER NOT NULL, " +
                            "part_id INTEGER NOT NULL, " +
                            "PRIMARY KEY (product_id, part_id), " +
                            "FOREIGN KEY (product_id) REFERENCES products(id), " +
                            "FOREIGN KEY (part_id) REFERENCES parts(id)" +
                            ")"
            );
        }
    }
}
