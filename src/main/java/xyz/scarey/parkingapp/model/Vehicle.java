package xyz.scarey.parkingapp.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vehicle {

    private StringProperty vin = new SimpleStringProperty();
    private StringProperty plate = new SimpleStringProperty();
    private StringProperty state = new SimpleStringProperty();
    private IntegerProperty year = new SimpleIntegerProperty();
    private StringProperty make = new SimpleStringProperty();
    private StringProperty model = new SimpleStringProperty();
    private StringProperty color = new SimpleStringProperty();

    public String getVin() {
        return vin.get();
    }

    public StringProperty vinProperty() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin.set(vin);
    }

    public String getPlate() {
        return plate.get();
    }

    public StringProperty plateProperty() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate.set(plate);
    }

    public String getState() {
        return state.get();
    }

    public StringProperty stateProperty() {
        return state;
    }

    public void setState(String state) {
        this.state.set(state);
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

    public String getMake() {
        return make.get();
    }

    public StringProperty makeProperty() {
        return make;
    }

    public void setMake(String make) {
        this.make.set(make);
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public String getColor() {
        return color.get();
    }

    public StringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }
}
