package jp.co.axa.apidemo.repositories;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import jp.co.axa.apidemo.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class EmployeeCachedRepository {

    @Autowired
    private EmployeeRepository employeeRepository;

    private LoadingCache<Long, Employee> employeeCache;

    @PostConstruct
    public void init() {
        employeeCache = Caffeine.newBuilder()
                .expireAfterAccess(60, TimeUnit.MINUTES)
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<Long, Employee>() {
                    @Override
                    public Employee load(Long key) {
                        System.out.println("Get from DB: " + key);
                        return employeeRepository.findById(key).orElse(null);
                    }
                });
    }

    // Don't call this method in RELEASE since the performance is low
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void deleteById(Long employeeId) {
        employeeRepository.deleteById(employeeId);
        employeeCache.invalidate(employeeId);
    }

    public Employee save(Employee employee) {
        final Employee savedEmployee = employeeRepository.save(employee);
        employeeCache.put(savedEmployee.getId(), savedEmployee);
        return savedEmployee;
    }

    public Optional<Employee> findById(Long employeeId) {
        return Optional.ofNullable(employeeCache.get(employeeId));
    }
}
