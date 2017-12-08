package xyz.scarey.parkingapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import xyz.scarey.parkingapp.Database;
import xyz.scarey.parkingapp.model.Vehicle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VehicleTabController {
    private Database database;
    private Connection connection;
    private ObservableList<Vehicle> data;

    @FXML
    private TableView<Vehicle> tableView;
    @FXML private TableColumn<Vehicle, String> vin;
    @FXML private TableColumn<Vehicle, String> plate;
    @FXML private TableColumn<Vehicle, String> state;
    @FXML private TableColumn<Vehicle, String> year;
    @FXML private TableColumn<Vehicle, String> make;
    @FXML private TableColumn<Vehicle, String> model;
    @FXML private TableColumn<Vehicle, String> color;

    @FXML private TextField fieldVin;
    @FXML private TextField fieldPlate;
    @FXML private TextField fieldState;
    @FXML private TextField fieldYear;
    @FXML private TextField fieldMake;
    @FXML private TextField fieldModel;
    @FXML private TextField fieldColor;

    @FXML
    void initialize() {
        assert tableView != null;

        vin.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vin"));
        plate.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("plate"));
        state.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("state"));
        year.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("year"));
        make.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("make"));
        model.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("model"));
        color.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("color"));

        database = new Database();
        try {
            connection = database.getConnection();
            buildData();
        } catch(ClassNotFoundException e) {

        } catch(SQLException e) {

        }
    }

    public void buildData() {
        data = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT vin, plate, state, year, make, model, color FROM Vehicles";
            ResultSet rs = connection.createStatement().executeQuery(SQL);
            while(rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVin(rs.getString("vin"));
                vehicle.setPlate(rs.getString("plate"));
                vehicle.setState(rs.getString("state"));
                vehicle.setYear(rs.getInt("year"));
                vehicle.setMake(rs.getString("make"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setColor(rs.getString("color"));
                data.addAll(vehicle);
            }
            tableView.setItems(data);
        } catch(Exception e) {

        }
    }

    @FXML
    private void onAddVehicle() {
        String vin;
        String plate;
        String state;
        int year;
        String make;
        String model;
        String color;

        if(fieldVin.getText().trim().isEmpty() || fieldPlate.getText().trim().isEmpty() || fieldState.getText().trim().isEmpty() || fieldYear.getText().trim().isEmpty() || fieldMake.getText().trim().isEmpty() || fieldModel.getText().trim().isEmpty() || fieldColor.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding vehicle");
            alert.setHeaderText("Error adding vehicle");
            alert.setContentText("There cannot be empty fields!");
            alert.showAndWait();
            return;
        }

        try {
            vin = fieldVin.getText().trim();
            plate = fieldPlate.getText().trim();
            state = fieldState.getText().trim();
            year = Integer.parseInt(fieldYear.getText().trim());
            make = fieldMake.getText().trim();
            model = fieldModel.getText().trim();
            color = fieldColor.getText().trim();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding vehicle");
            alert.setHeaderText("Error adding vehicle");
            alert.setContentText("Make sure the year is an integer!");
            alert.showAndWait();
            return;
        }

        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO Vehicles VALUES (" +
                    "'" + vin.toUpperCase() + "', '" + plate.toUpperCase() + "', '" + state.toUpperCase() + "', " + year + ", '" + make.toUpperCase() + "', '" + model.toUpperCase() + "', '" + color.toUpperCase() + "')";
            System.out.println(SQL);
            statement.executeUpdate(SQL);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Vehicle added");
            alert.setHeaderText("Vehicle added");
            alert.setContentText("The vehicle has been added successfully!");
            buildData();
            alert.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding vehicle");
            alert.setHeaderText("Error adding vehicle");
            alert.setContentText("The vin/plate/state combination used already exists!");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    private void onRemove() {
        SelectionModel<Vehicle> selection = tableView.getSelectionModel();
        Vehicle vehicle = selection.getSelectedItem();

        try {
            Statement statement = connection.createStatement();
            String SQL = "DELETE FROM Vehicles WHERE vin = '" + vehicle.getVin() + "' and plate = '" + vehicle.getPlate() + "' and state = '" + vehicle.getState() + "'";
            statement.executeUpdate(SQL);
            buildData();

        } catch(Exception e) {

        }
    }
}
