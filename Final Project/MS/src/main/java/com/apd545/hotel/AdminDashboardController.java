package com.apd545.hotel;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class AdminDashboardController {

    @FXML private TableView<Reservation> tblReservations;
    @FXML private TableColumn<Reservation, Integer> colId;
    @FXML private TableColumn<Reservation, String> colGuest;
    @FXML private TableColumn<Reservation, String> colRoom;
    @FXML private TableColumn<Reservation, String> colStatus;
    @FXML private TableColumn<Reservation, String> colCheckIn;
    @FXML private TableColumn<Reservation, String> colCheckOut;
    @FXML private TableColumn<Reservation, Double> colTotal;

    @FXML private TextField txtSearchName;
    @FXML private TextField txtSearchPhone;
    @FXML private DatePicker dpFrom;
    @FXML private DatePicker dpTo;
    @FXML private ComboBox<String> cbStatus;
    @FXML private ComboBox<String> cbSort;

    @FXML private Label lblMessage;

    private final ReservationService service = ReservationService.getInstance();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        colGuest.setCellValueFactory(c -> new ReadOnlyStringWrapper(safe(c.getValue().getGuestName())));
        colRoom.setCellValueFactory(c -> new ReadOnlyStringWrapper(safe(c.getValue().getRoomName())));
        colStatus.setCellValueFactory(c -> new ReadOnlyStringWrapper(safe(c.getValue().getStatus())));

        colCheckIn.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(c.getValue().getCheckIn() != null ?
                        c.getValue().getCheckIn().toString() : "-"));

        colCheckOut.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(c.getValue().getCheckOut() != null ?
                        c.getValue().getCheckOut().toString() : "-"));

        colTotal.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getTotal()));

        cbStatus.setItems(FXCollections.observableArrayList(
                "All", "Booked", "Cancelled", "Checked-out"
        ));
        cbStatus.getSelectionModel().select("All");

        cbSort.setItems(FXCollections.observableArrayList(
                "ID", "Name", "Check-In", "Status"
        ));
        cbSort.getSelectionModel().select("ID");

        loadReservations();
    }

    private String safe(String v) {
        return v == null ? "" : v;
    }

    private void loadReservations() {
        tblReservations.setItems(FXCollections.observableArrayList(service.getAll()));
    }

    /* ===================== FILTERS ===================== */

    @FXML
    private void handleApplyFilters() {

        List<Reservation> list = service.getAll();

        String name = txtSearchName.getText().trim().toLowerCase();
        if (!name.isEmpty()) {
            list.removeIf(r -> r.getGuestName() == null ||
                    !r.getGuestName().toLowerCase().contains(name));
        }

        String phone = txtSearchPhone.getText().trim();
        if (!phone.isEmpty()) {
            list.removeIf(r -> r.getPhone() == null ||
                    !r.getPhone().contains(phone));
        }

        LocalDate from = dpFrom.getValue();
        LocalDate to = dpTo.getValue();

        if (from != null) {
            list.removeIf(r -> r.getCheckIn() == null ||
                    r.getCheckIn().isBefore(from));
        }
        if (to != null) {
            list.removeIf(r -> r.getCheckOut() == null ||
                    r.getCheckOut().isAfter(to));
        }

        String status = cbStatus.getValue();
        if (!"All".equals(status)) {
            list.removeIf(r -> !status.equals(r.getStatus()));
        }

        String sort = cbSort.getValue();
        switch (sort) {
            case "Name" -> list.sort((a, b) -> safe(a.getGuestName())
                    .compareToIgnoreCase(safe(b.getGuestName())));
            case "Check-In" -> list.sort((a, b) -> a.getCheckIn().compareTo(b.getCheckIn()));
            case "Status" -> list.sort((a, b) -> safe(a.getStatus())
                    .compareToIgnoreCase(safe(b.getStatus())));
            default -> list.sort((a, b) -> b.getId() - a.getId());
        }

        tblReservations.setItems(FXCollections.observableArrayList(list));
        lblMessage.setText("Filters applied.");
    }

    @FXML
    private void handleResetFilters() {
        txtSearchName.clear();
        txtSearchPhone.clear();
        dpFrom.setValue(null);
        dpTo.setValue(null);
        cbStatus.getSelectionModel().select("All");
        cbSort.getSelectionModel().select("ID");

        loadReservations();
        lblMessage.setText("Filters reset.");
    }

    /* ===================== HELPER ===================== */

    private Reservation getSelected() {
        Reservation r = tblReservations.getSelectionModel().getSelectedItem();
        if (r == null) {
            lblMessage.setText("Please select a reservation first.");
        }
        return r;
    }

    /* ===================== BUTTONS ===================== */

    @FXML
    private void handleRefresh() {
        loadReservations();
        lblMessage.setText("List refreshed.");
    }

    @FXML
    private void handleCreate() {

        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("CreateReservation.fxml"));
            Parent ui = loader.load();

            Stage st = new Stage();
            st.setTitle("Create Reservation");
            st.setScene(new Scene(ui));
            st.initModality(Modality.APPLICATION_MODAL);
            st.showAndWait();

            loadReservations();
            lblMessage.setText("New reservation added.");

        } catch (Exception e) {
            e.printStackTrace();
            lblMessage.setText("Could not open create window.");
        }
    }


    @FXML
    private void handleEdit() {

        Reservation r = getSelected();
        if (r == null) return;

        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("EditReservation.fxml"));
            Parent ui = loader.load();

            EditReservationController ctrl = loader.getController();
            ctrl.init(r);

            Stage st = new Stage();
            st.setTitle("Edit Reservation");
            st.setScene(new Scene(ui));
            st.initModality(Modality.APPLICATION_MODAL);
            st.showAndWait();

            loadReservations();
            lblMessage.setText("Reservation updated.");

        } catch (Exception e) {
            e.printStackTrace();
            lblMessage.setText("Failed to open edit window.");
        }
    }


    @FXML
    private void handleCancel() {
        Reservation r = getSelected();
        if (r == null) return;

        if ("Cancelled".equals(r.getStatus())) {
            lblMessage.setText("Already cancelled.");
            return;
        }

        service.cancel(r);
        loadReservations();
        lblMessage.setText("Reservation cancelled.");
    }

    @FXML
    private void handleCheckout() {
        Reservation r = getSelected();
        if (r == null) return;

        if ("Checked-out".equals(r.getStatus())) {
            lblMessage.setText("Already checked out.");
            return;
        }

        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("CheckoutPopup.fxml"));
            Parent popup = loader.load();

            CheckoutPopupController ctrl = loader.getController();
            ctrl.init(r);

            Stage stage = new Stage();
            stage.setTitle("Checkout Payment");
            stage.setScene(new Scene(popup));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            loadReservations();
            lblMessage.setText("Checkout completed.");

        } catch (Exception e) {
            e.printStackTrace();
            lblMessage.setText("Error loading checkout window.");
        }
    }

    @FXML
    private void handleLogs() {
        App.setRoot("ActivityLog");
    }

    @FXML
    private void handleBackToLaunch() {
        App.setRoot("LaunchScreen");
    }

    @FXML
    private void handleLoyalty() {
        App.setRoot("LoyaltyDashboard");
    }
    @FXML
    private void handleFeedback() {
        App.setRoot("AdminFeedback");
    }
    @FXML
    private void handleReporting() {
        App.setRoot("Reporting");
    }

}
