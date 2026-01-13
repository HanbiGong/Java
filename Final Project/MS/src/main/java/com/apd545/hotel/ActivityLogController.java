package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ActivityLogController {

    @FXML private TableView<ActivityLog> tblLogs;
    @FXML private TableColumn<ActivityLog, Integer> colId;
    @FXML private TableColumn<ActivityLog, String> colUser;
    @FXML private TableColumn<ActivityLog, String> colAction;
    @FXML private TableColumn<ActivityLog, String> colTimestamp;

    private final ActivityLogService logService = ActivityLogService.getInstance();

    @FXML
    public void initialize() {

        // column mapping
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("username"));  // 🔥 변경됨
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
        colTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        // load log data
        tblLogs.getItems().setAll(logService.getAll());
    }

    @FXML
    private void handleBack() {
        App.setRoot("AdminDashboard");
    }
}
