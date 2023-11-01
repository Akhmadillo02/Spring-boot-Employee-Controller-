package uz.najottalim.imtixontask.service;

import org.springframework.stereotype.Service;
import uz.najottalim.imtixontask.entity.Employee;
import uz.najottalim.imtixontask.entity.TimeSheet;
import uz.najottalim.imtixontask.repository.EmployeeRepository;
import uz.najottalim.imtixontask.repository.TimeSheetRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TimeSheetRepository timeSheetRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, TimeSheetRepository timeSheetRepository) {
        this.employeeRepository = employeeRepository;
        this.timeSheetRepository = timeSheetRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null);
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Double calculateMonthlySalary(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            return 0d;
        }

        double dailySalary = employee.getSalary() / 30;
        List<TimeSheet> timeSheets = timeSheetRepository.findByEmployeeId(employeeId);
        int totalWorkDays = timeSheets.size();
        int totalLateDays = 0;
        for (TimeSheet timeSheet : timeSheets) {
            LocalDateTime checkInTime = timeSheet.getCheckInTime();
            LocalDateTime expectedCheckInTime = checkInTime.withHour(8).withMinute(0).withSecond(0);
            if (checkInTime.isAfter(expectedCheckInTime)) {
                totalLateDays++;
            }
        }

        int totalAbsentDays = 30 - totalWorkDays;
        double monthlySalary = (totalWorkDays * dailySalary) - (totalLateDays * dailySalary) - (totalAbsentDays * dailySalary);

        return monthlySalary;
    }

    @Override
    public Map<String, Integer> getMonthlyStatistics() {
        Map<String, Integer> monthlyStatistics = new HashMap<>();

        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            List<TimeSheet> timeSheets = timeSheetRepository.findByEmployeeId(employee.getId());
            int workedDays = timeSheets.size();
            monthlyStatistics.put(employee.getFirstName() + " " + employee.getLastName(), workedDays);
        }

        return monthlyStatistics;
    }

    @Override
    public Map<String, Integer> getDailyStatistics() {
        Map<String, Integer> dailyStatistics = new HashMap<>();

        LocalDate currentDate = LocalDate.now();
        List<TimeSheet> timeSheets = timeSheetRepository.findAllByCheckInTimeBetween(currentDate.atStartOfDay(), currentDate.atTime(LocalTime.MAX));

        for (TimeSheet timeSheet : timeSheets) {
            String date = timeSheet.getCheckInTime().toLocalDate().toString();
            dailyStatistics.put(date, dailyStatistics.getOrDefault(date, 0) + 1);
        }

        return dailyStatistics;
    }

    @Override
    public List<Map<String, Object>> getWorkedEmployeesInMonth() {
        List<Map<String, Object>> workedEmployees = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        LocalDate startOfMonth = currentDate.withDayOfMonth(1);
        LocalDate endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            List<TimeSheet> timeSheets = timeSheetRepository.findByEmployeeIdAndCheckInTimeBetween(employee.getId(), startOfMonth.atStartOfDay(), endOfMonth.atTime(LocalTime.MAX));
            if (!timeSheets.isEmpty()) {
                Map<String, Object> employeeData = new HashMap<>();
                employeeData.put("employee", employee);
                employeeData.put("workedDays", timeSheets.size());
                workedEmployees.add(employeeData);
            }
        }

        return workedEmployees;
    }

    @Override
    public List<Map<String, Object>> getLateEmployees() {
        List<Map<String, Object>> lateEmployees = new ArrayList<>();

        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();
        LocalDate previousDate = currentDate.minusDays(1);

        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            List<TimeSheet> timeSheets = timeSheetRepository.findByEmployeeIdAndCheckInTimeBetween(employee.getId(), previousDate.atTime(LocalTime.MIN), currentDate.atTime(currentTime.minusMinutes(15)));
            if (timeSheets.isEmpty()) {
                Map<String, Object> employeeData = new HashMap<>();
                employeeData.put("employee", employee);
                lateEmployees.add(employeeData);
            }
        }

        return lateEmployees;
    }
}

