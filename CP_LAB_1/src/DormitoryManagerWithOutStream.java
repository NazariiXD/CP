import models.Student;

import java.util.*;

public class DormitoryManagerWithOutStream implements IManager {
    private final List<Student> students;

    public DormitoryManagerWithOutStream(List<Student> students) {
        this.students = students;
    }

    @Override
    public Map<Boolean, List<Student>> partitionByBeneficiary() {
        var partitionedStudents = new HashMap<Boolean, List<Student>>();
        partitionedStudents.put(true, new ArrayList<>());
        partitionedStudents.put(false, new ArrayList<>());

        for (var student : students) {
            partitionedStudents.get(student.isBeneficiary()).add(student);
        }

        return partitionedStudents;
    }

    @Override
    public Map<String, List<Student>> groupByDormitory() {
        var groupedByDormitory = new HashMap<String, List<Student>>();

        for (var student : students) {
            groupedByDormitory
                    .computeIfAbsent(student.getDormitory(), s -> new ArrayList<>())
                    .add(student);
        }

        return groupedByDormitory;
    }

    @Override
    public Map<Integer, Long> countStudentsByRoom() {
        var roomCount = new HashMap<Integer, Long>();

        for (var student : students) {
            roomCount.put(
                    student.getRoomNumber(),
                    roomCount.getOrDefault(student.getRoomNumber(), 0L) + 1L);
        }

        return roomCount;
    }

    @Override
    public List<Student> sortByAgeAndBeneficiary() {
        var sortedStudents = new ArrayList<Student>(students);

        sortedStudents.sort(
                Comparator.comparingInt(Student::getAge)
                        .thenComparing(Student::isBeneficiary));

        return sortedStudents;
    }

    @Override
    public Set<Integer> getUniqueRoomNumbers() {
        var uniqueRoomNumbers = new HashSet<Integer>();

        for (var student : students) {
            uniqueRoomNumbers.add(student.getRoomNumber());
        }

        return uniqueRoomNumbers;
    }

    @Override
    public Optional<Student> findMaxFeeStudent() {
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
