import models.Student;

import java.util.*;
import java.util.stream.Collectors;

public class DormitoryManager {
    private final List<Student> students;

    public DormitoryManager(List<Student> students) {
        this.students = students;
    }

    public Map<Boolean, List<Student>> partitionByBeneficiary(){
        return students.stream()
                .collect(Collectors.partitioningBy(Student::isBeneficiary));
    }

    public Map<Boolean, List<Student>> partitionByBeneficiaryNoStream(){
        var partitionedStudents = new HashMap<Boolean, List<Student>>();
        partitionedStudents.put(true, new ArrayList<>());
        partitionedStudents.put(false, new ArrayList<>());

        for (var student : students) {
            partitionedStudents.get(student.isBeneficiary()).add(student);
        }

        return partitionedStudents;
    }

    public Map<String, List<Student>> groupByDormitory(){
        return students.stream()
                .collect(Collectors.groupingBy(Student::getDormitory));
    }

    public Map<String, List<Student>> groupByDormitoryNoStream() {
        var groupedByDormitory = new HashMap<String, List<Student>>();

        for (var student : students) {
            groupedByDormitory
                    .computeIfAbsent(student.getDormitory(), s -> new ArrayList<>())
                    .add(student);
        }

        return groupedByDormitory;
    }

    public Map<Integer, Long> countStudentsByRoom(){
        return students.stream()
                .collect(Collectors.groupingBy(Student::getRoomNumber, Collectors.counting()));
    }

    public Map<Integer, Integer> countStudentsByRoomNoStream(){
        var roomCount = new HashMap<Integer, Integer>();

        for (var student : students) {
            roomCount.put(
                    student.getRoomNumber(),
                    roomCount.getOrDefault(student.getRoomNumber(), 0) + 1);
        }

        return roomCount;
    }

    public List<Student> sortByAgeAndBeneficiary(){
        return students.stream()
                .sorted(Comparator.comparingInt(Student::getAge)
                        .thenComparing(Student::isBeneficiary))
                .collect(Collectors.toList());
    }

    public List<Student> sortByAgeAndBeneficiaryNoStream(){
        var sortedStudents = new ArrayList<Student>(students);

        sortedStudents.sort(
                Comparator.comparingInt(Student::getAge)
                        .thenComparing(Student::isBeneficiary));

        return sortedStudents;
    }

    public Set<Integer> getUniqueRoomNumbers(){
        return students.stream()
                .map(Student::getRoomNumber)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getUniqueRoomNumbersNoStream(){
        var uniqueRoomNumbers = new HashSet<Integer>();

        for (var student : students) {
            uniqueRoomNumbers.add(student.getRoomNumber());
        }

        return uniqueRoomNumbers;
    }

    public Optional<Student> findMaxFeeStudent() {
        return students.stream()
                .max(Comparator.comparingDouble(Student::getFee));
    }

    public Optional<Student> findMaxFeeStudentNoStream() {
        if (students.isEmpty()){
            return Optional.empty();
        }

        var maxFeeStudent = students.getFirst();

        for (var student : students) {
            if (student.getFee() > maxFeeStudent.getFee()){
                maxFeeStudent = student;
            }
        }

        return Optional.of(maxFeeStudent);
    }
}
