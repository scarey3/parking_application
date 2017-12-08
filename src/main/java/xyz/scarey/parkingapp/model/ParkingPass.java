package xyz.scarey.parkingapp.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ParkingPass {

    private IntegerProperty ownerId = new SimpleIntegerProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty vin = new SimpleStringProperty();
    private IntegerProperty year = new SimpleIntegerProperty();
    private StringProperty type = new SimpleStringProperty();

    public int getOwnerId() {
        return ownerId.get();
    }

    public IntegerProperty ownerIdProperty() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId.set(ownerId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getVin() {
        return vin.get();
    }

    public StringProperty vinProperty() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin.set(vin);
    }

    public int getYear() {
        return year.get();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }
}
