package org.example.models;

import com.google.gson.annotations.Expose;

import java.io.Serial;
import java.io.Serializable;

public class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    @Expose
    private String licensePlate;

    @Expose
    private Brand brand;
    transient private int year;

    public Vehicle() {}

    public Vehicle(String licensePlate, Brand brand, int year) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        var yearInfo = (year != 0) ? String.valueOf(year) : "N/A";
        var brandInfo = (brand != null) ? brand.toString() : "N/A";

        return """
                licensePlate: "%s"
                brand: %s
                year: %s
                """.formatted(licensePlate, brandInfo, yearInfo);
    }
}
