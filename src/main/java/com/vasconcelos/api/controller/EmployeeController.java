package com.vasconcelos.api.controller;

import com.vasconcelos.api.data.mapper.EmployeeMapper;
import com.vasconcelos.api.data.vo.v1.EmployeeVO;
import com.vasconcelos.api.model.Employee;
import com.vasconcelos.api.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

;

@RestController
@RequestMapping(value = "/api/v1/employee")
@Tag(name = "Endpoint Employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Return employe by id")
    public EmployeeVO getById(@PathVariable UUID id) {
        EmployeeVO employee = employeeMapper.toVo(employeeService.get(id));
        employee.add(linkTo(methodOn(EmployeeController.class).getById(id)).withSelfRel());
        employee.add(linkTo(methodOn(EmployeeController.class).get(null)).withRel(IanaLinkRelations.COLLECTION));

        return employee;
    }

    @GetMapping
    @Operation(summary = "Return all employees")
    public Page<EmployeeVO> get(Pageable pageable) {
        Page<Employee> employees = employeeService.getAll(pageable);
        Page<EmployeeVO> employeesVo = employees
                .map(employee -> employeeMapper.toVo(employee))
                .map(employeeVO ->
                        employeeVO.add(linkTo(methodOn(EmployeeController.class).getById(employeeVO.getId())).withSelfRel()));

        return employeesVo;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create new employee")
    public EmployeeVO create(@RequestBody EmployeeVO employee) {
        return employeeMapper.toVo(employeeService.create(employeeMapper.toEntity(employee)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update existing empoyee by id")
    public void update(@PathVariable UUID id, @RequestBody EmployeeVO newEmployye) {
        employeeService.update(id, employeeMapper.toEntity(newEmployye));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete employee by id")
    public void delete(@PathVariable UUID id) {
        employeeService.delete(id);
    }
}
