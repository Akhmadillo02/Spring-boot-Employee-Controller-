package uz.najottalim.imtixontask.service;

import uz.najottalim.imtixontask.entity.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    void saveEmployee(Employee employee);
    void deleteEmployee(Long id);
    Double calculateMonthlySalary(Long employeeId);
    Map<String, Integer> getMonthlyStatistics();
    Map<String, Integer> getDailyStatistics();
    List<Map<String, Object>> getWorkedEmployeesInMonth();
    List<Map<String, Object>> getLateEmployees();
}