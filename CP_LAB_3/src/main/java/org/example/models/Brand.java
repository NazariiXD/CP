package org.example.models;

import com.google.gson.annotations.Expose;

import java.io.Serial;
import java.io.Serializable;

public class Brand implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Expose
    private String name;

    @Expose
    private String country;

    public Brand() {}

    public Brand(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "name: %s, country: %s".formatted(name, country);
    }
}
