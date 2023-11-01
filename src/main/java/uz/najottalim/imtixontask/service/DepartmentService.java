package uz.najottalim.imtixontask.service;

import org.springframework.stereotype.Service;
import uz.najottalim.imtixontask.entity.Department;
import uz.najottalim.imtixontask.repository.DepartmentRepository;

import java.util.List;

@Service
public class DepartmentService {


private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with id: " + id));
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department department = getDepartmentById(id);
        department.setFirstName(updatedDepartment.getFirstName());
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        Department department = getDepartmentById(id);
        departmentRepository.delete(department);
    }
}
