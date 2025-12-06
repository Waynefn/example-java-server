package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.RecordNotFoundException;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Setter
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) throws RecordNotFoundException {
        final Employee employee = employeeService.getEmployee(employeeId);
        if (employee == null) {
            throw new RecordNotFoundException(employeeId);
        }
        return employee;
    }

    @PostMapping("/employees")
    public void saveEmployee(Employee employee){
        validateEmployee(employee);
        employeeService.saveEmployee(employee);
        System.out.println("Employee Saved Successfully");
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId) throws RecordNotFoundException {
        try{
            employeeService.deleteEmployee(employeeId);
        } catch (Exception e) {
            throw new RecordNotFoundException(employeeId);
        }

        System.out.println("Employee Deleted Successfully");
    }

    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId){
        validateEmployee(employee);
        final Employee emp = employeeService.getEmployee(employeeId);
        if(emp != null){
            employeeService.updateEmployee(employee);
        }
    }

    private static void validateEmployee(Employee employee) throws IllegalArgumentException {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        if (employee.getSalary() == null || employee.getSalary() < 0) {
            throw new IllegalArgumentException("Employee Name cannot be null or negative.");
        }
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new IllegalArgumentException("Employee Name cannot be null or empty.");
        }
        if (employee.getDepartment() == null || employee.getDepartment().isEmpty()) {
            throw new IllegalArgumentException("Employee Department cannot be null or empty.");
        }
    }
}
