package org.example.services;

import org.example.interfaces.VehicleDataHandler;
import org.example.models.Vehicle;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleSerializationYamlHandler implements VehicleDataHandler {
    private static final Yaml yaml;

    static {
        var options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        yaml = new Yaml(options);
    }

    @Override
    public void write(List<Vehicle> vehicles, String fileName) throws IllegalArgumentException, IOException {
        if (vehicles == null) {
            throw new IllegalArgumentException("The list of vehicles cannot be null");
        }

        validateFilePath(fileName);

        var filteredVehicles = vehicles.stream()
                .map(this::clearBrandForOldVehicles)
                .collect(Collectors.toList());

        try (var writer = new FileWriter(fileName)) {
            yaml.dump(filteredVehicles, writer);
        }
    }

    @Override
    public List<Vehicle> read(String fileName) throws IllegalArgumentException, IOException {
        validateFilePath(fileName);

        try (var reader = new FileReader(fileName)) {
            return yaml.load(reader);
        }
    }

    private Vehicle clearBrandForOldVehicles(Vehicle vehicle) {
        if (vehicle.getYear() < 2010) {
            return new Vehicle(vehicle.getLicensePlate(), null, vehicle.getYear());
        }

        return vehicle;
    }

    private void validateFilePath(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("The file name cannot be null or empty");
        }

        if (!fileName.endsWith(".yaml")) {
            throw new IllegalArgumentException("The file must have a .yaml extension");
        }
    }
}
