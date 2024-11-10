import models.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var students = getStudents();

        IManager manager;

        System.out.println("Введіть 1 із стрімом або 2 без:");
        int choice = scanner.nextInt();

        if (choice == 1) {
            manager = new DormitoryManagerWithStream(students);
        } else if (choice == 2) {
            manager = new DormitoryManagerWithOutStream(students);
        } else {
            System.out.println("Невірний вибір, використовуємо ManagerA за замовчуванням.");
            manager = new DormitoryManagerWithStream(students);
        }

        System.out.println("Partitioning students into beneficiaries and non-beneficiaries:");
        printPartitionedStudents(manager.partitionByBeneficiary());


        System.out.println("\nGrouping students by dormitories:");
        printGroupedDormitoryStudents(manager.groupByDormitory());

        System.out.println("\nCounting students in each room:");
        printRoomCount(manager.countStudentsByRoom());

        System.out.println("\nSorting students by age and beneficiary status:");
        manager.sortByAgeAndBeneficiary().forEach(System.out::println);

        System.out.println("\nUnique room numbers:");
        System.out.println(manager.getUniqueRoomNumbers());

        System.out.println("\nStudent with the highest fee:");
        manager.findMaxFeeStudent().ifPresentOrElse(
                student -> System.out.println("Student with max fee: \n" + student),
                () -> System.out.println("Student not found")
        );
    }

    private static void printPartitionedStudents(Map<Boolean, List<Student>> partitionedStudents) {
        partitionedStudents.forEach((isBeneficiary, students) -> {
            System.out.println(isBeneficiary ? "Beneficiaries:" : "Non-beneficiaries:");
            students.forEach(System.out::println);
        });
    }

    private static void printGroupedDormitoryStudents(Map<String, List<Student>> groupedStudents) {
        groupedStudents.forEach((dormitory, students) -> {
            System.out.println("Dormitory: " + dormitory);
            students.forEach(System.out::println);
        });
    }

    private static void printRoomCount(Map<Integer, ? extends Number> roomCount) {
        roomCount.forEach((roomNumber, count) ->
                System.out.println("Room " + roomNumber + ": " + count + " students")
        );
    }

    private static List<Student> getStudents(){
        return Arrays.asList(
                new Student("John", "Doe", "Dormitory A", 101, 500.0, 20, true),
                new Student("Jane", "Smith", "Dormitory B", 102, 450.0, 21, false),
                new Student("Mary", "Johnson", "Dormitory A", 101, 550.0, 22, true),
                new Student("James", "Brown", "Dormitory C", 103, 600.0, 23, false),
                new Student("Patricia", "Taylor", "Dormitory B", 104, 480.0, 19, true),
                new Student("Robert", "Anderson", "Dormitory A", 105, 530.0, 24, false),
                new Student("Linda", "Thomas", "Dormitory C", 106, 620.0, 22, true),
                new Student("Michael", "Jackson", "Dormitory B", 107, 470.0, 21, false),
                new Student("Barbara", "White", "Dormitory A", 108, 510.0, 20, true),
                new Student("William", "Harris", "Dormitory C", 109, 590.0, 23, false)
        );
    }
}