package uz.najottalim.imtixontask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.najottalim.imtixontask.entity.TimeSheet;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeSheetRepository extends JpaRepository<TimeSheet,Long> {
    List<TimeSheet> findByEmployeeId(Long employeeId);

    List<TimeSheet> findAllByCheckInTimeBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);

    List<TimeSheet> findByEmployeeIdAndCheckInTimeBetween(Long id, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
