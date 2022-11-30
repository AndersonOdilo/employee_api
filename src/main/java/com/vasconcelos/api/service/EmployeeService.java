package com.vasconcelos.api.service;

import com.vasconcelos.api.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    Employee get(UUID id);

    Page<Employee> getAll(Pageable pageable);

    Employee create(Employee employee);

    void update(UUID id, Employee employee);

    void delete(UUID id);

}
