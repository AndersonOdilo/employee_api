package com.vasconcelos.api.service.impl;

import com.vasconcelos.api.exception.AlreadyUsedElementException;
import com.vasconcelos.api.exception.NoSuchElementFoundException;
import com.vasconcelos.api.model.Employee;
import com.vasconcelos.api.repository.EmployeeRepository;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private EmployeeServiceImpl employeeService;


    private Employee createValidateEmployeeNotId() {
        return new Employee(
                null,
                "Anderson",
                "Vasconcelos",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "andersonodilo@hotmail.com",
                "123456789012");
    }

    private Employee createValidateEmployeeId() {
        return new Employee(
                UUID.randomUUID(),
                "Joao",
                "Vasconcelos",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "joao@hotmail.com",
                "987654321987");
    }

    private Page<Employee> createListValidateEmployees() {
        List<Employee> employees = Arrays.asList(createValidateEmployeeId(), createValidateEmployeeId());
        PageRequest pageable = PageRequest.of(1, 10);

        return new PageImpl<>(employees, pageable, employees.size());
    }

    @Test
    public void getSuccessTest() {
        Employee employee = createValidateEmployeeId();
        UUID id = employee.getId();

        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.of(employee));

        Employee employeeResponse = employeeService.get(id);

        Assertions.assertThat(employeeResponse)
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    public void getdNotFoundExceptionTest() {
        UUID id = UUID.randomUUID();

        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(NoSuchElementFoundException.class)
                .isThrownBy(() -> employeeService.get(id));
    }

    @Test
    public void deleteSucessTest() {
        Employee employee = createValidateEmployeeId();
        UUID id = employee.getId();

        Mockito.when(employeeRepository.existsById(Mockito.any())).thenReturn(true);

        Assertions.assertThatNoException().isThrownBy(() -> employeeService.delete(id));
    }

    @Test
    public void deleteNotFoundExceptionTest() {
        UUID id = UUID.randomUUID();

        Mockito.when(employeeRepository.existsById(Mockito.any())).thenReturn(false);

        Assertions.assertThatExceptionOfType(NoSuchElementFoundException.class)
                .isThrownBy(() -> employeeService.delete(id));
    }

    @Test
    public void createSuccessTest() {
        Employee employee = createValidateEmployeeNotId();

        Mockito.when(validator.validate(Mockito.any())).thenReturn(Collections.emptySet());
        Mockito.when(employeeRepository.existsByEmail(Mockito.any())).thenReturn(Boolean.FALSE);
        Mockito.when(employeeRepository.existsByNis(Mockito.any())).thenReturn(Boolean.FALSE);
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        Employee employeeResponse = employeeService.create(employee);

        Assertions.assertThat(employeeResponse).usingRecursiveComparison().isEqualTo(employee);
    }

    @Test
    public void createExistsEmailTest() {
        Employee employee = createValidateEmployeeNotId();

        Mockito.when(validator.validate(Mockito.any())).thenReturn(Collections.emptySet());
        Mockito.when(employeeRepository.existsByEmail(Mockito.any())).thenReturn(Boolean.TRUE);

        Assertions.assertThatExceptionOfType(AlreadyUsedElementException.class)
                .isThrownBy(() -> employeeService.create(employee));
    }

    @Test
    public void createExistsNisTest() {
        Employee employee = createValidateEmployeeNotId();

        Mockito.when(validator.validate(Mockito.any())).thenReturn(Collections.emptySet());
        Mockito.when(employeeRepository.existsByEmail(Mockito.any())).thenReturn(Boolean.FALSE);
        Mockito.when(employeeRepository.existsByNis(Mockito.any())).thenReturn(Boolean.TRUE);

        Assertions.assertThatExceptionOfType(AlreadyUsedElementException.class)
                .isThrownBy(() -> employeeService.create(employee));
    }

    @Test
    public void updateSuccessTest() {
        Employee employee = createValidateEmployeeId();
        UUID id = employee.getId();

        Mockito.when(validator.validate(Mockito.any())).thenReturn(Collections.emptySet());
        Mockito.when(employeeRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(employeeRepository.existsByEmailAndIdNot(Mockito.any(), Mockito.any())).thenReturn(Boolean.FALSE);
        Mockito.when(employeeRepository.existsByNisAndIdNot(Mockito.any(), Mockito.any())).thenReturn(Boolean.FALSE);

        Assertions.assertThatNoException().isThrownBy(() -> employeeService.update(id, employee));
    }

    @Test
    public void updateNotFoundTest() {
        Employee employee = createValidateEmployeeId();
        UUID id = employee.getId();

        Mockito.when(validator.validate(Mockito.any())).thenReturn(Collections.emptySet());
        Mockito.when(employeeRepository.existsById(Mockito.any())).thenReturn(false);

        Assertions.assertThatExceptionOfType(NoSuchElementFoundException.class)
                .isThrownBy(() -> employeeService.update(id, employee));
    }

    @Test
    public void updateSExistsEmailTest() {
        Employee employee = createValidateEmployeeId();
        UUID id = employee.getId();

        Mockito.when(validator.validate(Mockito.any())).thenReturn(Collections.emptySet());
        Mockito.when(employeeRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(employeeRepository.existsByEmailAndIdNot(Mockito.any(), Mockito.any())).thenReturn(Boolean.TRUE);

        Assertions.assertThatExceptionOfType(AlreadyUsedElementException.class)
                .isThrownBy(() -> employeeService.update(id, employee));
    }

    @Test
    public void updateExistsNisTest() {
        Employee employee = createValidateEmployeeId();
        UUID id = employee.getId();

        Mockito.when(validator.validate(Mockito.any())).thenReturn(Collections.emptySet());
        Mockito.when(employeeRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(employeeRepository.existsByEmailAndIdNot(Mockito.any(), Mockito.any())).thenReturn(Boolean.FALSE);
        Mockito.when(employeeRepository.existsByNisAndIdNot(Mockito.any(), Mockito.any())).thenReturn(Boolean.TRUE);

        Assertions.assertThatExceptionOfType(AlreadyUsedElementException.class)
                .isThrownBy(() -> employeeService.update(id, employee));
    }

    @Test
    public void findAllTest() {
        Page<Employee> employees = createListValidateEmployees();
        Mockito.when(employeeRepository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(employees);

        Pageable pageRequest = PageRequest.of(1, 10);
        Page<Employee> pagedEmployeesReturn = employeeService.getAll(pageRequest);

        Assertions.assertThat(pagedEmployeesReturn.getNumberOfElements()).isEqualTo(2);
    }
}
