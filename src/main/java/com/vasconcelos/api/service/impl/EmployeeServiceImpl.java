package com.vasconcelos.api.service.impl;

import com.vasconcelos.api.exception.AlreadyUsedElementException;
import com.vasconcelos.api.exception.NoSuchElementFoundException;
import com.vasconcelos.api.model.Employee;
import com.vasconcelos.api.repository.EmployeeRepository;
import com.vasconcelos.api.service.EmployeeService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Validator validator;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, Validator validator) {
        this.employeeRepository = employeeRepository;
        this.validator = validator;
    }

    @Override
    public Employee get(UUID id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new NoSuchElementFoundException("Employee not found");
    }

    @Override
    public Page<Employee> getAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void create(Employee employee) {
        validate(employee);

        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new AlreadyUsedElementException("the email is already in use");
        }

        if (employeeRepository.existsByNis(employee.getNis())) {
            throw new AlreadyUsedElementException("the PIS is already in use");
        }

        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void update(UUID id, Employee employee) {
        validate(employee);

        if (!employeeRepository.existsById(id)) {
            throw new NoSuchElementFoundException("Employee not found");
        }

        if (employeeRepository.existsByEmailAndIdNot(employee.getEmail(), id)) {
            throw new AlreadyUsedElementException("the email is already in use");
        }

        if (employeeRepository.existsByNisAndIdNot(employee.getNis(), id)) {
            throw new AlreadyUsedElementException("the PIS is already in use");
        }

        employee.setId(id);
        employeeRepository.save(employee);
    }

    @Override
    public void delete(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new NoSuchElementFoundException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }

    private void validate(Employee employee) {
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
