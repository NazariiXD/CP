import models.Student;

import java.util.*;
import java.util.stream.Collectors;

public class DormitoryManagerWithStream implements IManager {
    private final List<Student> students;

    public DormitoryManagerWithStream(List<Student> students) {
        this.students = students;
    }

    @Override
    public Map<Boolean, List<Student>> partitionByBeneficiary(){
        return students.stream()
                .collect(Collectors.partitioningBy(Student::isBeneficiary));
    }

    @Override
    public Map<String, List<Student>> groupByDormitory() {
        return students.stream()
                .collect(Collectors.groupingBy(Student::getDormitory));
    }

    @Override
    public Map<Integer, Long> countStudentsByRoom(){
        return students.stream()
                .collect(Collectors.groupingBy(Student::getRoomNumber, Collectors.counting()));
    }

    @Override
    public List<Student> sortByAgeAndBeneficiary(){
        return students.stream()
                .sorted(Comparator.comparingInt(Student::getAge)
                        .thenComparing(Student::isBeneficiary))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Integer> getUniqueRoomNumbers(){
        return students.stream()
                .map(Student::getRoomNumber)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Student> findMaxFeeStudent() {
        return students.stream()
                .max(Comparator.comparingDouble(Student::getFee));
    }
}
