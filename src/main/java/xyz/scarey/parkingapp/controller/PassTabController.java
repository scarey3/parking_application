package xyz.scarey.parkingapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import xyz.scarey.parkingapp.Database;
import xyz.scarey.parkingapp.model.ParkingPass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PassTabController {

    private Database database;
    private Connection connection;
    private ObservableList<ParkingPass> data;

    @FXML private TableView<ParkingPass> tableView;
    @FXML private TableColumn<ParkingPass, Integer> ownerId;
    @FXML private TableColumn<ParkingPass, String> firstName;
    @FXML private TableColumn<ParkingPass, String> lastName;
    @FXML private TableColumn<ParkingPass, String> vin;
    @FXML private TableColumn<ParkingPass, Integer> year;
    @FXML private TableColumn<ParkingPass, String> type;

    @FXML private TextField ppId;
    @FXML private TextField ppVin;
    @FXML private TextField ppYear;
    @FXML private ChoiceBox<String> cbType;

    @FXML private TextField fieldId;
    @FXML private TextField fieldFirstName;
    @FXML private TextField fieldLastName;
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

        ownerId.setCellValueFactory(new PropertyValueFactory<ParkingPass, Integer>("ownerId"));
        firstName.setCellValueFactory(new PropertyValueFactory<ParkingPass, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<ParkingPass, String>("lastName"));
        vin.setCellValueFactory(new PropertyValueFactory<ParkingPass, String>("vin"));
        year.setCellValueFactory(new PropertyValueFactory<ParkingPass, Integer>("year"));
        type.setCellValueFactory(new PropertyValueFactory<ParkingPass, String>("type"));

        cbType.setItems(FXCollections.observableArrayList("Commuter", "Residential", "Faculty"));

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
            String SQL = "SELECT ParkingPasses.owner_id, first_name, last_name, ParkingPasses.vin, ParkingPasses.year, type FROM ParkingPasses, People, Vehicles WHERE ParkingPasses.owner_id = People.id AND ParkingPasses.vin = Vehicles.vin";
            ResultSet rs = connection.createStatement().executeQuery(SQL);
            while(rs.next()) {
                ParkingPass parkingPass = new ParkingPass();
                parkingPass.setOwnerId(rs.getInt("owner_id"));
                parkingPass.setFirstName(rs.getString("first_name"));
                parkingPass.setLastName(rs.getString("last_name"));
                parkingPass.setVin(rs.getString("vin"));
                parkingPass.setYear(rs.getInt("year"));
                parkingPass.setType(rs.getString("type"));
                data.addAll(parkingPass);
            }
            tableView.setItems(data);
        } catch(Exception e) {

        }
    }

    @FXML
    private void onAddPerson() {
        int id;
        String firstName;
        String lastName;

        if(fieldFirstName.getText().trim().isEmpty() || fieldLastName.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding user");
            alert.setHeaderText("Error adding user");
            alert.setContentText("There cannot be empty fields!");
            alert.showAndWait();
            return;
        }

        try {
            if(!fieldId.getText().trim().isEmpty()) {
                id = Integer.parseInt(fieldId.getText().trim());
            } else {
                id = -1;
            }
            firstName = fieldFirstName.getText().trim();
            lastName = fieldLastName.getText().trim();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding user");
            alert.setHeaderText("Error adding user");
            alert.setContentText("Make sure the ID is an integer!");
            alert.showAndWait();
            return;
        }

        try {
            Statement statement = connection.createStatement();
            String SQL;
            if(id >= 0) {
                SQL = "INSERT INTO People VALUES (" +
                        id + ",'" + firstName.toUpperCase() + "', '" + lastName.toUpperCase() + "')";
            } else {
                SQL = "INSERT INTO People (first_name, last_name) VALUES ('" +
                        firstName.toUpperCase() + "', '" + lastName.toUpperCase() + "')";
            }
            System.out.println(SQL);
            statement.executeUpdate(SQL);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("User added");
            alert.setHeaderText("User added");
            alert.setContentText("The user has been added successfully!");
            alert.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding user");
            alert.setHeaderText("Error adding user");
            alert.setContentText("The ID used already exists!");
            alert.showAndWait();
            return;
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
    private void onAddParkingPass() {
        int id;
        String vin;
        int year;
        String type;

        if(ppId.getText().trim().isEmpty() || ppVin.getText().trim().isEmpty() || ppYear.getText().trim().isEmpty() || cbType.getValue().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding parking pass");
            alert.setHeaderText("Error adding parking pass");
            alert.setContentText("There cannot be empty fields!");
            alert.showAndWait();
            return;
        }

        try {
            id = Integer.parseInt(ppId.getText().trim());
            vin = ppVin.getText().trim();
            year = Integer.parseInt(ppYear.getText().trim());
            type = cbType.getValue().trim();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding parking pass");
            alert.setHeaderText("Error adding parking pass");
            alert.setContentText("Make sure the ID and year are integers!");
            alert.showAndWait();
            return;
        }

        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO ParkingPasses VALUES (" +
                    id + ", '" + vin + "', " + year + ", '" + type + "')";
            System.out.println(SQL);
            statement.executeUpdate(SQL);

            buildData();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Parking pass added");
            alert.setHeaderText("Parking pass added");
            alert.setContentText("The parking pass has been added successfully!");
            alert.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding parking pass");
            alert.setHeaderText("Error adding parking pass");
            alert.setContentText("The id/vin/year combination used already exists!");
            alert.showAndWait();
            return;
        }

    }

    @FXML
    private void onRemove() {
        SelectionModel<ParkingPass> selection = tableView.getSelectionModel();
        ParkingPass selectedPass = selection.getSelectedItem();

        try {
            Statement statement = connection.createStatement();
            String SQL = "DELETE FROM ParkingPasses WHERE owner_id = " + selectedPass.getOwnerId() + " and vin = '" + selectedPass.getVin() + "' and year = " + selectedPass.getYear();
            statement.executeUpdate(SQL);
            buildData();

        } catch(Exception e) {

        }
    }
}
