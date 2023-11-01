package uz.najottalim.imtixontask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.najottalim.imtixontask.entity.Employee;
import uz.najottalim.imtixontask.service.EmployeeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/saveEmployee")
    public ResponseEntity<Void> saveEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/monthly-salary")
    public ResponseEntity<Double> calculateMonthlySalary(@PathVariable Long id) {
        Double monthlySalary = employeeService.calculateMonthlySalary(id);
        return ResponseEntity.ok(monthlySalary);
    }

    @GetMapping("/monthly-statistics")
    public ResponseEntity<Map<String, Integer>> getMonthlyStatistics() {
        Map<String, Integer> monthlyStatistics = employeeService.getMonthlyStatistics();
        return ResponseEntity.ok(monthlyStatistics);
    }

    @GetMapping("/daily-statistics")
    public ResponseEntity<Map<String, Integer>> getDailyStatistics() {
        Map<String, Integer> dailyStatistics = employeeService.getDailyStatistics();
        return ResponseEntity.ok(dailyStatistics);
    }

    @GetMapping("/worked-employees-in-month")
    public ResponseEntity<List<Map<String, Object>>> getWorkedEmployeesInMonth() {
        List<Map<String, Object>> workedEmployees = employeeService.getWorkedEmployeesInMonth();
        return ResponseEntity.ok(workedEmployees);
    }

    @GetMapping("/lateEmployees")
    public ResponseEntity<List<Map<String, Object>>> getLateEmployees() {
        List<Map<String, Object>> lateEmployees = employeeService.getLateEmployees();
        return ResponseEntity.ok(lateEmployees);
    }



}