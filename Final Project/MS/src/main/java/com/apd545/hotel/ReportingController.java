package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ReportingController {

    // Revenue table
    @FXML private TableView<RevenueRow> tblRevenue;
    @FXML private TableColumn<RevenueRow, String> colRevDate;
    @FXML private TableColumn<RevenueRow, String> colRevRoom;
    @FXML private TableColumn<RevenueRow, Integer> colRevNights;
    @FXML private TableColumn<RevenueRow, Double> colRevAmount;

    // Occupancy table
    @FXML private TableView<OccupancyRow> tblOcc;
    @FXML private TableColumn<OccupancyRow, String> colOccDate;
    @FXML private TableColumn<OccupancyRow, Integer> colOccTotal;
    @FXML private TableColumn<OccupancyRow, Integer> colOccUsed;
    @FXML private TableColumn<OccupancyRow, Double> colOccPercent;

    // Logs table
    @FXML private TableView<ActivityLog> tblLog;
    @FXML private TableColumn<ActivityLog, String> colLogTime;
    @FXML private TableColumn<ActivityLog, String> colLogUser;
    @FXML private TableColumn<ActivityLog, String> colLogAction;

    private final ReservationService reservationService = ReservationService.getInstance();
    private final ActivityLogService logService = ActivityLogService.getInstance();

    @FXML
    public void initialize() {
        loadRevenue();
        loadOccupancy();
        loadLogs();
    }

    /* ------------------ Revenue Report ------------------ */
    private void loadRevenue() {

        colRevDate.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().date()));

        colRevRoom.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().room()));

        colRevNights.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(c.getValue().nights()));

        colRevAmount.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(c.getValue().amount()));

        List<Reservation> list = reservationService.getAll();
        List<RevenueRow> rows = new ArrayList<>();

        for (Reservation r : list) {

            if ("Checked-out".equals(r.getStatus())) {

                long nights = 0;
                if (r.getCheckIn() != null && r.getCheckOut() != null) {
                    nights = ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());
                }

                rows.add(new RevenueRow(
                        r.getCheckIn() != null ? r.getCheckIn().toString() : "-",
                        r.getRoomName(),
                        (int) nights,
                        r.getTotal()
                ));
            }
        }

        tblRevenue.getItems().setAll(rows);
    }

    /* ------------------ Occupancy Report ------------------ */
    private void loadOccupancy() {

        colOccDate.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().date()));

        colOccTotal.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(c.getValue().total()));

        colOccUsed.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(c.getValue().used()));

        colOccPercent.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(c.getValue().percent()));

        List<Reservation> list = reservationService.getAll();

        Map<LocalDate, Integer> usedMap = new HashMap<>();

        for (Reservation r : list) {
            if (r.getCheckIn() != null &&
                    (Objects.equals(r.getStatus(), "Booked") ||
                            Objects.equals(r.getStatus(), "Checked-out"))) {

                LocalDate d = r.getCheckIn();
                usedMap.put(d, usedMap.getOrDefault(d, 0) + 1);
            }
        }

        int TOTAL_ROOMS = 20;

        List<OccupancyRow> rows = new ArrayList<>();
        for (var e : usedMap.entrySet()) {
            double pct = (e.getValue() * 100.0) / TOTAL_ROOMS;

            rows.add(new OccupancyRow(
                    e.getKey().toString(),
                    TOTAL_ROOMS,
                    e.getValue(),
                    pct
            ));
        }

        tblOcc.getItems().setAll(rows);
    }

    /* ------------------ Activity Logs ------------------ */
    private void loadLogs() {

        colLogTime.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getTimestamp().toString()
                ));

        colLogUser.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getUsername()
                ));

        colLogAction.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getAction()
                ));

        tblLog.getItems().setAll(logService.getAll());
    }

    /* ------------------ CSV Export Button ------------------ */
    @FXML
    private void onExportCsv() {

        FileChooser ch = new FileChooser();
        ch.setTitle("Save CSV");
        ch.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File f = ch.showSaveDialog(null);
        if (f == null) return;

        CsvUtil.exportReport(
                f,
                tblRevenue.getItems(),
                tblOcc.getItems(),
                tblLog.getItems()
        );
    }

    /* ------------------ PDF Export Button ------------------ */
    @FXML
    private void onExportPdf() {

        FileChooser ch = new FileChooser();
        ch.setTitle("Save PDF");
        ch.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        File f = ch.showSaveDialog(null);
        if (f == null) return;

        PdfUtil.exportReport(
                f,
                tblRevenue.getItems(),
                tblOcc.getItems(),
                tblLog.getItems()
        );
    }

    /* ------------------ Back Button ------------------ */
    @FXML
    private void onBackToHome() {
        App.setRoot("AdminDashboard");
    }

    /* Simple Row Records */
    public record RevenueRow(String date, String room, int nights, double amount) {}
    public record OccupancyRow(String date, int total, int used, double percent) {}

}

