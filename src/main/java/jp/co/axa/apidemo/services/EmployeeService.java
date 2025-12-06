package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.lang.Nullable;

import java.util.List;

public interface EmployeeService {

    public List<Employee> retrieveEmployees();

    @Nullable
    public Employee getEmployee(Long employeeId);

    public void saveEmployee(Employee employee);

    public void deleteEmployee(Long employeeId);

    public void updateEmployee(Employee employee);
}
