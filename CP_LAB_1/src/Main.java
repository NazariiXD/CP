import models.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        var students = getStudents();

        var manager = new DormitoryManager(students);

        System.out.println("Partitioning students into beneficiaries and non-beneficiaries:");
        System.out.println("With stream");
        printPartitionedStudents(manager.partitionByBeneficiary());

        System.out.println("No stream");
        printPartitionedStudents(manager.partitionByBeneficiaryNoStream());

        System.out.println("\nGrouping students by dormitories:");
        System.out.println("With stream");
        printGroupedDormitoryStudents(manager.groupByDormitory());

        System.out.println("No stream");
        printGroupedDormitoryStudents(manager.groupByDormitoryNoStream());

        System.out.println("\nCounting students in each room:");
        System.out.println("With stream");
        printRoomCount(manager.countStudentsByRoom());

        System.out.println("No stream");
        printRoomCount(manager.countStudentsByRoomNoStream());

        System.out.println("\nSorting students by age and beneficiary status:");
        System.out.println("With stream");
        manager.sortByAgeAndBeneficiary().forEach(System.out::println);

        System.out.println("No stream");
        manager.sortByAgeAndBeneficiaryNoStream().forEach(System.out::println);

        System.out.println("\nUnique room numbers:");
        System.out.println("With stream");
        System.out.println(manager.getUniqueRoomNumbers());

        System.out.println("No stream");
        System.out.println(manager.getUniqueRoomNumbersNoStream());

        System.out.println("\nStudent with the highest fee:");
        System.out.println("With stream");
        manager.findMaxFeeStudent().ifPresentOrElse(
                student -> System.out.println("Student with max fee: \n" + student),
                () -> System.out.println("Student not found")
        );

        System.out.println("No stream");
        manager.findMaxFeeStudentNoStream().ifPresentOrElse(
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