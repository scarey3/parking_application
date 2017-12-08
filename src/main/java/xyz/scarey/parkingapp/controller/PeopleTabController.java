package xyz.scarey.parkingapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import xyz.scarey.parkingapp.Database;
import xyz.scarey.parkingapp.model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PeopleTabController {

    private Database database;
    private Connection connection;
    private ObservableList<Person> data;

    @FXML private TableView<Person> tableView;
    @FXML private TableColumn<Person, Integer> id;
    @FXML private TableColumn<Person, String> firstName;
    @FXML private TableColumn<Person, String> lastName;

    @FXML private TextField fieldId;
    @FXML private TextField fieldFirstName;
    @FXML private TextField fieldLastName;

    @FXML
    void initialize() {
        assert tableView != null;

        id.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

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
            String SQL = "SELECT id, first_name, last_name FROM People";
            ResultSet rs = connection.createStatement().executeQuery(SQL);
            while(rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                data.addAll(person);
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
            alert.setContentText("First name and last name fields cannot be empty!");
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

            buildData();

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
    private void onRemove() {
        SelectionModel<Person> selection = tableView.getSelectionModel();
        Person person = selection.getSelectedItem();

        try {
            Statement statement = connection.createStatement();
            String SQL = "DELETE FROM People WHERE id = " + person.getId();
            statement.executeUpdate(SQL);
            buildData();

        } catch(Exception e) {

        }
    }
}
