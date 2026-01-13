package com.apd545.hotel;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AdminFeedbackController {

    @FXML private TableView<Reservation> tblFeedback;
    @FXML private TableColumn<Reservation, String> colGuest;
    @FXML private TableColumn<Reservation, String> colDate;
    @FXML private TableColumn<Reservation, Integer> colRating;
    @FXML private TableColumn<Reservation, String> colComment;
    @FXML private TableColumn<Reservation, String> colSentiment;

    @FXML private DatePicker dpFrom;
    @FXML private DatePicker dpTo;

    private final ReservationService service = ReservationService.getInstance();

    @FXML
    public void initialize() {

        colGuest.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getGuestName()));

        colDate.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getCheckOut() == null ? "-" : c.getValue().getCheckOut().toString()
                ));

        colRating.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getRating()));

        colComment.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getComments()));

        // sentiment from DB field
        colSentiment.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getSentiment()));

        loadData();
    }

    private void loadData() {
        List<Reservation> list = service.getAll();

        List<Reservation> filtered = list.stream()
                .filter(r -> "Checked-out".equals(r.getStatus()))
                .filter(r -> r.getRating() > 0)
                .collect(Collectors.toList());

        tblFeedback.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void handleFilter() {

        LocalDate from = dpFrom.getValue();
        LocalDate to = dpTo.getValue();

        List<Reservation> list = service.getAll();

        List<Reservation> filtered = list.stream()
                .filter(r -> "Checked-out".equals(r.getStatus()))
                .filter(r -> r.getRating() > 0)
                .filter(r -> {
                    LocalDate co = r.getCheckOut();
                    if (co == null) return false;

                    if (from != null && co.isBefore(from)) return false;
                    if (to != null && co.isAfter(to)) return false;

                    return true;
                })
                .collect(Collectors.toList());

        tblFeedback.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void handleReset() {
        dpFrom.setValue(null);
        dpTo.setValue(null);
        loadData();
    }

    @FXML
    private void handleReports() {
        App.setRoot("Reporting"); // 연결
    }

    @FXML
    private void handleBack() {
        App.setRoot("AdminDashboard");
    }
}
