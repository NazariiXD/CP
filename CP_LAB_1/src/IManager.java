import models.Student;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IManager {
    Map<Boolean, List<Student>> partitionByBeneficiary();
    Map<String, List<Student>> groupByDormitory();
    Map<Integer, Long> countStudentsByRoom();
    List<Student> sortByAgeAndBeneficiary();
    Set<Integer> getUniqueRoomNumbers();
    Optional<Student> findMaxFeeStudent();
}
