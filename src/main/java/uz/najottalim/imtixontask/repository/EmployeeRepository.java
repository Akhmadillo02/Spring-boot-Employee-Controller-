package uz.najottalim.imtixontask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.najottalim.imtixontask.entity.Employee;

import java.nio.file.LinkOption;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
