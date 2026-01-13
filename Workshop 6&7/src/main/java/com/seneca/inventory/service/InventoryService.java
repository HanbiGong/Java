/**********************************************
 Workshop # 4, 5, 6 & 7
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-11-28
 **********************************************/
package com.seneca.inventory.service;

import com.google.inject.Inject;
import com.seneca.inventory.db.DbUtil;
import com.seneca.inventory.model.InHouse;
import com.seneca.inventory.model.Outsourced;
import com.seneca.inventory.model.Part;
import com.seneca.inventory.model.Product;
import com.seneca.inventory.repository.PartRepository;
import com.seneca.inventory.repository.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.sql.*;

// XML parser
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * Service class for parts and products.
 * Uses repositories, XML file, and SQLite database.
 */
public class InventoryService {

    private final PartRepository partRepo;
    private final ProductRepository productRepo;

    private int nextPartId = 1;
    private int nextProductId = 1;

    @Inject
    public InventoryService(PartRepository partRepo, ProductRepository productRepo) {
        this.partRepo = partRepo;
        this.productRepo = productRepo;
        loadSampleData();
    }

    // -----------------------------
    // ID helpers
    // -----------------------------

    public int peekNextPartId() {
        return nextPartId;
    }

    public int generateNextPartId() {
        return nextPartId++;
    }

    public int peekNextProductId() {
        return nextProductId;
    }

    public int generateNextProductId() {
        return nextProductId++;
    }

    // =============================
    // PART CRUD
    // =============================

    public ObservableList<Part> getAllParts() {
        return partRepo.getAll();
    }

    public void addInHousePart(String name, double price, int stock, int min, int max, int machineId) {
        validatePart(name, price, stock, min, max);
        InHouse part = new InHouse(generateNextPartId(), name, price, stock, min, max, machineId);
        partRepo.add(part);
    }

    public void addOutsourcedPart(String name, double price, int stock, int min, int max, String companyName) {
        validatePart(name, price, stock, min, max);
        Outsourced part = new Outsourced(generateNextPartId(), name, price, stock, min, max, companyName);
        partRepo.add(part);
    }

    public void updatePart(Part target,
                           String name,
                           double price,
                           int stock,
                           int min,
                           int max,
                           boolean inHouse,
                           Integer machineId,
                           String companyName) {

        if (target == null) {
            throw new IllegalArgumentException("Part not selected.");
        }

        validatePart(name, price, stock, min, max);

        // common fields
        target.setName(name);
        target.setPrice(price);
        target.setStock(stock);
        target.setMin(min);
        target.setMax(max);

        // change type if needed
        if (inHouse) {
            if (!(target instanceof InHouse)) {
                InHouse newPart = new InHouse(target.getId(), name, price, stock, min, max, machineId);
                replacePart(target, newPart);
            } else {
                ((InHouse) target).setMachineId(machineId);
            }
        } else {
            if (!(target instanceof Outsourced)) {
                Outsourced newPart = new Outsourced(target.getId(), name, price, stock, min, max, companyName);
                replacePart(target, newPart);
            } else {
                ((Outsourced) target).setCompanyName(companyName);
            }
        }
    }

    private void replacePart(Part oldP, Part newP) {
        partRepo.remove(oldP);
        partRepo.add(newP);
    }

    public void deletePart(Part part) {
        partRepo.remove(part);
    }

    public ObservableList<Part> searchParts(String keyword) {
        return partRepo.searchByName(keyword);
    }

    private void validatePart(String name, double price, int stock, int min, int max) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (min > max) {
            throw new IllegalArgumentException("Min cannot be greater than max.");
        }
        if (stock < min || stock > max) {
            throw new IllegalArgumentException("Stock is out of range.");
        }
    }

    // =============================
    // PRODUCT CRUD
    // =============================

    public ObservableList<Product> getAllProducts() {
        return productRepo.getAll();
    }

    public ObservableList<Product> searchProducts(String keyword) {
        return productRepo.searchByName(keyword);
    }

    public Product addProduct(String name,
                              double price,
                              int stock,
                              int min,
                              int max,
                              ObservableList<Part> associatedParts) {

        validateProduct(name, price, stock, min, max, associatedParts);

        Product product = new Product(generateNextProductId(), name, price, stock, min, max);
        for (Part p : associatedParts) {
            product.addAssociatedPart(p);
        }

        productRepo.add(product);
        return product;
    }

    public void updateProduct(Product product,
                              String name,
                              double price,
                              int stock,
                              int min,
                              int max,
                              ObservableList<Part> associatedParts) {

        validateProduct(name, price, stock, min, max, associatedParts);

        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setMin(min);
        product.setMax(max);

        product.getAllAssociatedParts().clear();
        product.getAllAssociatedParts().addAll(associatedParts);
    }

    // rule: cannot delete if product has parts
    public boolean deleteProduct(Product product) {
        if (!product.getAllAssociatedParts().isEmpty()) {
            return false;
        }
        productRepo.remove(product);
        return true;
    }

    private void validateProduct(String name,
                                 double price,
                                 int stock,
                                 int min,
                                 int max,
                                 ObservableList<Part> parts) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (min > max) {
            throw new IllegalArgumentException("Min cannot be greater than max.");
        }
        if (stock < min || stock > max) {
            throw new IllegalArgumentException("Stock is out of range.");
        }
        if (parts == null || parts.isEmpty()) {
            throw new IllegalArgumentException("Product must have at least one part.");
        }

        double totalCost = 0;
        for (Part p : parts) {
            totalCost += p.getPrice();
        }
        if (price < totalCost) {
            throw new IllegalArgumentException("Product price is less than sum of parts.");
        }
    }

    // =============================
    // XML FILE SAVE / LOAD
    // =============================

    // Simple XML writer
    public void saveToXmlFile(File file) {
        if (file == null) {
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("<inventory>\n");

            // parts
            writer.write("  <parts>\n");
            for (Part p : partRepo.getAll()) {
                writer.write("    <part>\n");
                writer.write("      <id>" + p.getId() + "</id>\n");
                writer.write("      <name>" + safe(p.getName()) + "</name>\n");
                writer.write("      <price>" + p.getPrice() + "</price>\n");
                writer.write("      <stock>" + p.getStock() + "</stock>\n");
                writer.write("      <min>" + p.getMin() + "</min>\n");
                writer.write("      <max>" + p.getMax() + "</max>\n");

                if (p instanceof InHouse ih) {
                    writer.write("      <type>IN_HOUSE</type>\n");
                    writer.write("      <machineId>" + ih.getMachineId() + "</machineId>\n");
                } else if (p instanceof Outsourced os) {
                    writer.write("      <type>OUTSOURCED</type>\n");
                    writer.write("      <companyName>" + safe(os.getCompanyName()) + "</companyName>\n");
                }

                writer.write("    </part>\n");
            }
            writer.write("  </parts>\n");

            // products
            writer.write("  <products>\n");
            for (Product product : productRepo.getAll()) {
                writer.write("    <product>\n");
                writer.write("      <id>" + product.getId() + "</id>\n");
                writer.write("      <name>" + safe(product.getName()) + "</name>\n");
                writer.write("      <price>" + product.getPrice() + "</price>\n");
                writer.write("      <stock>" + product.getStock() + "</stock>\n");
                writer.write("      <min>" + product.getMin() + "</min>\n");
                writer.write("      <max>" + product.getMax() + "</max>\n");

                writer.write("      <associatedParts>\n");
                for (Part p : product.getAllAssociatedParts()) {
                    writer.write("        <partId>" + p.getId() + "</partId>\n");
                }
                writer.write("      </associatedParts>\n");

                writer.write("    </product>\n");
            }
            writer.write("  </products>\n");

            writer.write("</inventory>\n");

        } catch (IOException ex) {
            throw new RuntimeException("Could not save to XML file.", ex);
        }
    }

    // Load parts and products from XML file
    public void loadFromXmlFile(File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File does not exist.");
        }

        try {
            // clear current data
            partRepo.getAll().clear();
            productRepo.getAll().clear();

            int maxPartId = 0;
            int maxProductId = 0;

            // parse xml document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            // ===== load parts =====
            NodeList partNodes = doc.getElementsByTagName("part");
            for (int i = 0; i < partNodes.getLength(); i++) {
                Node node = partNodes.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                Element e = (Element) node;

                int id = Integer.parseInt(getChildText(e, "id"));
                String name = getChildText(e, "name");
                double price = Double.parseDouble(getChildText(e, "price"));
                int stock = Integer.parseInt(getChildText(e, "stock"));
                int min = Integer.parseInt(getChildText(e, "min"));
                int max = Integer.parseInt(getChildText(e, "max"));
                String type = getChildText(e, "type");

                Part part;
                if ("IN_HOUSE".equalsIgnoreCase(type)) {
                    String machineText = getChildText(e, "machineId");
                    int machineId = machineText == null || machineText.isBlank()
                            ? 0
                            : Integer.parseInt(machineText);
                    part = new InHouse(id, name, price, stock, min, max, machineId);
                } else if ("OUTSOURCED".equalsIgnoreCase(type)) {
                    String companyName = getChildText(e, "companyName");
                    part = new Outsourced(id, name, price, stock, min, max, companyName);
                } else {
                    // default if type is missing
                    part = new InHouse(id, name, price, stock, min, max, 0);
                }

                partRepo.add(part);

                if (id > maxPartId) {
                    maxPartId = id;
                }
            }

            // ===== load products =====
            NodeList productNodes = doc.getElementsByTagName("product");
            for (int i = 0; i < productNodes.getLength(); i++) {
                Node node = productNodes.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                Element e = (Element) node;

                int id = Integer.parseInt(getChildText(e, "id"));
                String name = getChildText(e, "name");
                double price = Double.parseDouble(getChildText(e, "price"));
                int stock = Integer.parseInt(getChildText(e, "stock"));
                int min = Integer.parseInt(getChildText(e, "min"));
                int max = Integer.parseInt(getChildText(e, "max"));

                Product product = new Product(id, name, price, stock, min, max);

                // find associatedParts section
                NodeList assocList = e.getElementsByTagName("associatedParts");
                if (assocList != null && assocList.getLength() > 0) {
                    Element assocRoot = (Element) assocList.item(0);
                    NodeList partIdNodes = assocRoot.getElementsByTagName("partId");

                    for (int j = 0; j < partIdNodes.getLength(); j++) {
                        Node n = partIdNodes.item(j);
                        if (n.getNodeType() != Node.ELEMENT_NODE) {
                            continue;
                        }

                        Element pEl = (Element) n;
                        String partIdText = pEl.getTextContent();
                        if (partIdText == null || partIdText.isBlank()) {
                            continue;
                        }

                        int partId = Integer.parseInt(partIdText.trim());
                        Part part = partRepo.findById(partId);
                        if (part != null) {
                            product.addAssociatedPart(part);
                        }
                    }
                }

                productRepo.add(product);

                if (id > maxProductId) {
                    maxProductId = id;
                }
            }

            // update next id values
            nextPartId = maxPartId + 1;
            nextProductId = maxProductId + 1;

        } catch (Exception ex) {
            throw new RuntimeException("Failed to load inventory from XML file.", ex);
        }
    }

    // small helper for XML
    private String getChildText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list == null || list.getLength() == 0) {
            return null;
        }
        Node node = list.item(0);
        if (node == null) {
            return null;
        }
        return node.getTextContent();
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    // =============================
    // DATABASE SAVE / LOAD
    // =============================

    // Save all data into SQLite DB
    public void saveToDatabase() {
        try {
            DbUtil.initSchema();

            try (Connection conn = DbUtil.getConnection()) {
                conn.setAutoCommit(false);

                // clear tables
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("DELETE FROM product_parts");
                    stmt.executeUpdate("DELETE FROM products");
                    stmt.executeUpdate("DELETE FROM parts");
                }

                // insert parts
                String insertPartSql =
                        "INSERT INTO parts (id, name, price, stock, min, max, type, machine_id, company_name) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertPartSql)) {
                    for (Part p : partRepo.getAll()) {
                        ps.setInt(1, p.getId());
                        ps.setString(2, p.getName());
                        ps.setDouble(3, p.getPrice());
                        ps.setInt(4, p.getStock());
                        ps.setInt(5, p.getMin());
                        ps.setInt(6, p.getMax());

                        if (p instanceof InHouse ih) {
                            ps.setString(7, "IN_HOUSE");
                            ps.setObject(8, ih.getMachineId());
                            ps.setObject(9, null);
                        } else if (p instanceof Outsourced os) {
                            ps.setString(7, "OUTSOURCED");
                            ps.setObject(8, null);
                            ps.setString(9, os.getCompanyName());
                        } else {
                            ps.setString(7, "UNKNOWN");
                            ps.setObject(8, null);
                            ps.setObject(9, null);
                        }

                        ps.addBatch();
                    }
                    ps.executeBatch();
                }

                // insert products
                String insertProductSql =
                        "INSERT INTO products (id, name, price, stock, min, max) " +
                                "VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertProductSql)) {
                    for (Product product : productRepo.getAll()) {
                        ps.setInt(1, product.getId());
                        ps.setString(2, product.getName());
                        ps.setDouble(3, product.getPrice());
                        ps.setInt(4, product.getStock());
                        ps.setInt(5, product.getMin());
                        ps.setInt(6, product.getMax());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }

                // insert product_parts links
                String insertLinkSql =
                        "INSERT INTO product_parts (product_id, part_id) VALUES (?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertLinkSql)) {
                    for (Product product : productRepo.getAll()) {
                        for (Part p : product.getAllAssociatedParts()) {
                            ps.setInt(1, product.getId());
                            ps.setInt(2, p.getId());
                            ps.addBatch();
                        }
                    }
                    ps.executeBatch();
                }

                conn.commit();
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Could not save to database.", ex);
        }
    }

    // Load data from SQLite DB
    public void loadFromDatabase() {
        try {
            DbUtil.initSchema();

            ObservableList<Part> loadedParts = FXCollections.observableArrayList();
            ObservableList<Product> loadedProducts = FXCollections.observableArrayList();
            Map<Integer, Product> productMap = new HashMap<>();

            try (Connection conn = DbUtil.getConnection()) {

                // load parts
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT * FROM parts")) {

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        double price = rs.getDouble("price");
                        int stock = rs.getInt("stock");
                        int min = rs.getInt("min");
                        int max = rs.getInt("max");
                        String type = rs.getString("type");
                        int machineId = rs.getInt("machine_id");
                        String companyName = rs.getString("company_name");

                        Part part;
                        if ("IN_HOUSE".equalsIgnoreCase(type)) {
                            part = new InHouse(id, name, price, stock, min, max, machineId);
                        } else if ("OUTSOURCED".equalsIgnoreCase(type)) {
                            part = new Outsourced(id, name, price, stock, min, max, companyName);
                        } else {
                            part = new InHouse(id, name, price, stock, min, max, 0);
                        }

                        loadedParts.add(part);
                    }
                }

                // load products
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        double price = rs.getDouble("price");
                        int stock = rs.getInt("stock");
                        int min = rs.getInt("min");
                        int max = rs.getInt("max");

                        Product product = new Product(id, name, price, stock, min, max);
                        loadedProducts.add(product);
                        productMap.put(id, product);
                    }
                }

                // replace current data with loaded data
                partRepo.getAll().clear();
                partRepo.getAll().addAll(loadedParts);

                productRepo.getAll().clear();
                productRepo.getAll().addAll(loadedProducts);

                // load relations
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT product_id, part_id FROM product_parts")) {

                    while (rs.next()) {
                        int pid = rs.getInt("product_id");
                        int partId = rs.getInt("part_id");

                        Product product = productMap.get(pid);
                        Part part = partRepo.findById(partId);

                        if (product != null && part != null) {
                            product.addAssociatedPart(part);
                        }
                    }
                }
            }

            // update next ids
            int maxPartId = 0;
            for (Part p : partRepo.getAll()) {
                if (p.getId() > maxPartId) {
                    maxPartId = p.getId();
                }
            }
            nextPartId = maxPartId + 1;

            int maxProductId = 0;
            for (Product p : productRepo.getAll()) {
                if (p.getId() > maxProductId) {
                    maxProductId = p.getId();
                }
            }
            nextProductId = maxProductId + 1;

        } catch (SQLException ex) {
            throw new RuntimeException("Could not load from database.", ex);
        }
    }

    // =============================
    // SAMPLE DATA
    // =============================

    private void loadSampleData() {
        if (!partRepo.getAll().isEmpty()) {
            return;
        }

        Part p1 = new InHouse(generateNextPartId(), "Wheel",     10, 10, 1, 20, 100);
        Part p2 = new Outsourced(generateNextPartId(), "Seat",   20, 5, 1, 10, "SeatCo");
        Part p3 = new InHouse(generateNextPartId(), "Pedal",     5,  12, 1, 30, 200);
        Part p4 = new Outsourced(generateNextPartId(), "Frame",  50, 3, 1, 10, "FrameFactory");
        Part p5 = new InHouse(generateNextPartId(), "Chain",     8,  7, 1, 20, 300);

        partRepo.add(p1);
        partRepo.add(p2);
        partRepo.add(p3);
        partRepo.add(p4);
        partRepo.add(p5);

        Product bike = new Product(generateNextProductId(), "Bike", 199, 5, 1, 10);
        bike.addAssociatedPart(p1);
        bike.addAssociatedPart(p2);

        Product miniBike = new Product(generateNextProductId(), "Mini Bike", 129, 8, 1, 12);
        miniBike.addAssociatedPart(p3);
        miniBike.addAssociatedPart(p5);

        Product scooter = new Product(generateNextProductId(), "Scooter", 99, 4, 1, 10);
        scooter.addAssociatedPart(p1);
        scooter.addAssociatedPart(p3);

        Product proBike = new Product(generateNextProductId(), "Pro Bike", 299, 3, 1, 8);
        proBike.addAssociatedPart(p4);
        proBike.addAssociatedPart(p2);

        Product kidsBike = new Product(generateNextProductId(), "Kids Bike", 159, 6, 1, 12);
        kidsBike.addAssociatedPart(p5);
        kidsBike.addAssociatedPart(p2);

        productRepo.add(bike);
        productRepo.add(miniBike);
        productRepo.add(scooter);
        productRepo.add(proBike);
        productRepo.add(kidsBike);
    }
}
