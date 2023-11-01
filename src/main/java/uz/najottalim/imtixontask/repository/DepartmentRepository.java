package uz.najottalim.imtixontask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.najottalim.imtixontask.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
