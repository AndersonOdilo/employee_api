package com.vasconcelos.api.model.employee;

import com.vasconcelos.api.model.Employee;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class EmployeeValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void employeeSuccessTest() {
        Employee employee = EmployeeBuildTest.builder()
                .setFirstName("Anderson")
                .setLastName("Vasconcelos")
                .setEmail("andersonodilo@hotmail.com")
                .setNis("1234567891")
                .get();


        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        Assertions.assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    public void employeeAllErrosTest() {
        Employee employee = EmployeeBuildTest.builder()
                .setFirstName("A")
                .setLastName("V")
                .setEmail("andersonodilohotmmail.com")
                .setNis("12345A67891")
                .get();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        Assertions.assertThat(violations.size()).isEqualTo(4);
    }

    @Test
    public void employeeFirstNameIsRequiredTest() {
        Employee employee = EmployeeBuildTest.builder()
                .setLastName("Vasconcelos")
                .setEmail("andersonodilo@hotmail.com")
                .setNis("1234567891")
                .get();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        ConstraintViolation<Employee> violation = violations.iterator().next();

        Assertions.assertThat(violations.isEmpty()).isFalse();
        Assertions.assertThat(violation.getMessage()).isEqualTo("First name is required");
    }

    @Test
    public void employeeFirstNameIsSmallTest() {
        Employee employee = EmployeeBuildTest.builder()
                .setFirstName("A")
                .setLastName("Vasconcelos")
                .setEmail("andersonodilo@hotmail.com")
                .setNis("1234567891")
                .get();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        ConstraintViolation<Employee> violation = violations.iterator().next();

        Assertions.assertThat(violations.isEmpty()).isFalse();
        Assertions.assertThat(violation.getMessage()).isEqualTo("First name must be beteween 2 to 30 characters");
    }

    @Test
    public void employeeFirstNameIsLargeTest() {
        Employee employee = EmployeeBuildTest.builder()
                .setFirstName("Anderson Odilo Veiga Vasconcelos")
                .setLastName("Vasconcelos")
                .setEmail("andersonodilo@hotmail.com")
                .setNis("1234567891")
                .get();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        ConstraintViolation<Employee> violation = violations.iterator().next();

        Assertions.assertThat(violations.isEmpty()).isFalse();
        Assertions.assertThat(violation.getMessage()).isEqualTo("First name must be beteween 2 to 30 characters");
    }

    @Test
    public void employeeLastNameIsRequiredTest() {
        Employee employee = EmployeeBuildTest.builder()
                .setFirstName("Anderson")
                .setEmail("andersonodilo@hotmail.com")
                .setNis("1234567891")
                .get();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        ConstraintViolation<Employee> violation = violations.iterator().next();

        Assertions.assertThat(violations.isEmpty()).isFalse();
        Assertions.assertThat(violation.getMessage()).isEqualTo("Last name is required");
    }

    @Test
    public void employeeLastNameIsSmallTest() {
        Employee employee = EmployeeBuildTest.builder()
                .setFirstName("Anderson")
                .setLastName("V")
                .setEmail("andersonodilo@hotmail.com")
                .setNis("1234567891")
                .get();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        ConstraintViolation<Employee> violation = violations.iterator().next();

        Assertions.assertThat(violations.isEmpty()).isFalse();
        Assertions.assertThat(violation.getMessage()).isEqualTo("Last name must be beteween 2 to 50 characters");
    }

    @Test
    public void employeeLastNameIsLargeTest() {
        Employee employee = EmployeeBuildTest.builder()
                .setFirstName("Anderson")
                .setLastName("Vasconcelos Andderson Odilo Veiga Vasconcelos vasconcelos vasconcelos vasconcelos")
                .setEmail("andersonodilo@hotmail.com")
                .setNis("1234567891")
                .get();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        ConstraintViolation<Employee> violation = violations.iterator().next();

        Assertions.assertThat(violations.isEmpty()).isFalse();
        Assertions.assertThat(violation.getMessage()).isEqualTo("Last name must be beteween 2 to 50 characters");
    }

    @Test
    public void employeeEmailIsRequiredTest() {
        Employee employee = EmployeeBuildTest.builder()
                .setFirstName("Anderson")
                .setLastName("Vasconcelos")
                .setNis("1234567891")
                .get();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        ConstraintViolation<Employee> violation = violations.iterator().next();

        Assertions.assertThat(violations.isEmpty()).isFalse();
        Assertions.assertThat(violation.getMessage()).isEqualTo("Email is required");
    }

    @Test
    public void employeeEmailIsNotValidTest() {
        Employee employee = EmployeeBuildTest.builder()
                .setFirstName("Anderson")
                .setLastName("Vasconcelos")
                .setEmail("andersonodilohotmail.com")
                .setNis("1234567891")
                .get();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        ConstraintViolation<Employee> violation = violations.iterator().next();

        Assertions.assertThat(violations.isEmpty()).isFalse();
        Assertions.assertThat(violation.getMessage()).isEqualTo("Email is not valid");
    }

    @Test
    public void employeeNisIsRequiredTest() {
        Employee employee = EmployeeBuildTest.builder()
                .setFirstName("Anderson")
                .setLastName("Vasconcelos")
                .setEmail("andersonodilo@hotmmail.com")
                .get();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        ConstraintViolation<Employee> violation = violations.iterator().next();

        Assertions.assertThat(violations.isEmpty()).isFalse();
        Assertions.assertThat(violation.getMessage()).isEqualTo("NIS is required");
    }

    @Test
    public void employeeNisIsAllowOnlyNumversTest() {
        Employee employee = EmployeeBuildTest.builder()
                .setFirstName("Anderson")
                .setLastName("Vasconcelos")
                .setEmail("andersonodilo@hotmmail.com")
                .setNis("12345A67891")
                .get();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        ConstraintViolation<Employee> violation = violations.iterator().next();

        Assertions.assertThat(violations.isEmpty()).isFalse();
        Assertions.assertThat(violation.getMessage()).isEqualTo("Nis is allow only numbers");
    }

}
