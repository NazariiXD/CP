package org.example;

import org.example.interfaces.DataHandler;
import org.example.interfaces.VehicleDataHandler;
import org.example.models.Brand;
import org.example.models.Vehicle;
import org.example.services.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String fileName = "vehicles";
    private static final String yearsFilePath = fileName + "-years.dat";
    private static final String licensePlatesFilePath = fileName + "-license-plates.dat";
    private static final String brandsFilePath = fileName + "-brands.dat";

    private static final DataHandler<Integer> yearDataHandler = new YearDataHandler();
    private static final DataHandler<String> licensePlateDataHandler = new LicensePlateDataHandler();
    private static final DataHandler<Brand> brandDataHandler = new BrandDataHandler();

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        var vehicles = getVehicles();
        var licensePlates = getLicensePlates(vehicles);
        var brands = getBrands(vehicles);
        var years = getYears(vehicles);

        VehicleDataHandler dataHandler = new VehicleSerializationHandler();
        String filePath = fileName + ".dat";

        while (true) {
            System.out.println("Виберіть формат серіалізації:");
            System.out.println("1 - Потоки вводу виводу");
            System.out.println("2 - Нативна серіалізація");
            System.out.println("3 - JSON серіалізація");
            System.out.println("4 - YAML серіалізація");
            System.out.println("0 - Вихід");

            try {

                var choice = scanner.nextInt();

                if (choice == 1) {
                    handleIODataSerialization(licensePlates, brands, years);
                }
                else {
                    switch (choice) {
                        case 2:
                            dataHandler = new VehicleSerializationHandler();
                            filePath = fileName + ".dat";
                            break;
                        case 3:
                            dataHandler = new VehicleSerializationJsonHandler();
                            filePath = fileName + ".json";
                            break;
                        case 4:
                            dataHandler = new VehicleSerializationYamlHandler();
                            filePath = fileName + ".yaml";
                            break;
                        case 0:
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Невірний вибір, використовуємо нативний за замовчуванням.");
                            dataHandler = new VehicleSerializationHandler();
                            filePath = fileName + ".txt";
                            break;
                    }

                    dataHandler.write(vehicles, filePath);
                    System.out.println("Сериалізовані транспортні засоби у файл: " + filePath);
                    System.out.println();

                    var deserializedVehicle = dataHandler.read(filePath);
                    System.out.println("Десеріалізовані транспортні засоби: ");
                    deserializedVehicle.forEach(System.out::println);
                }
            }
            catch (Exception e) {
                System.err.println("Помилка : " + e.getMessage());
            }
        }
    }

    private static void handleIODataSerialization(List<String> licensePlates, List<Brand> brands, List<Integer> years) throws IOException {
        yearDataHandler.write(years, yearsFilePath);
        licensePlateDataHandler.write(licensePlates, licensePlatesFilePath);
        brandDataHandler.write(brands, brandsFilePath);

        var newYears = yearDataHandler.read(yearsFilePath);
        var newLicensePlates = licensePlateDataHandler.read(licensePlatesFilePath);
        var newBrands = brandDataHandler.read(brandsFilePath);

        System.out.println("LicensePlates:");
        newLicensePlates.forEach(plate -> System.out.println((newLicensePlates.indexOf(plate) + 1) + ". " + plate));
        System.out.println();

        System.out.println("Brands:");
        newBrands.forEach(brand -> System.out.println((newBrands.indexOf(brand) + 1) + ". " + brand.toString()));
        System.out.println();

        System.out.println("Years:");
        newYears.forEach(year -> System.out.println((newYears.indexOf(year) + 1) + ". " + year.toString()));
        System.out.println();
    }

    private static List<Vehicle> getVehicles() {
        return List.of(
                new Vehicle("ABC123", new Brand("Toyota", "Japan"), 2015),
                new Vehicle("XYZ789", new Brand("BMW", "Germany"), 2020),
                new Vehicle("DEF456", new Brand("Ford", "USA"), 2018),
                new Vehicle("LMN345", new Brand("Tesla", "USA"), 2008),
                new Vehicle("QRS678", new Brand("Honda", "Japan"), 2019),
                new Vehicle("TUV901", new Brand("Audi", "Germany"), 2022),
                new Vehicle("WXY234", new Brand("Chevrolet", "USA"), 2017),
                new Vehicle("ZAB567", new Brand("Nissan", "Japan"), 2021),
                new Vehicle("JKL890", new Brand("Hyundai", "South Korea"), 2018),
                new Vehicle("MNO123", new Brand("Peugeot", "France"), 2005)
        );
    }

    private static List<Integer> getYears(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(Vehicle::getYear)
                .toList();
    }

    private static List<Brand> getBrands(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(Vehicle::getBrand)
                .toList();
    }

    private static List<String> getLicensePlates(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(Vehicle::getLicensePlate)
                .toList();
    }
}