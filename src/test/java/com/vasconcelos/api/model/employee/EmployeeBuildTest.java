package com.vasconcelos.api.model.employee;

import com.vasconcelos.api.model.Employee;

public class EmployeeBuildTest {

    private Employee employee;

    private EmployeeBuildTest(){
        employee = new Employee();
    }

    public static EmployeeBuildTest builder(){
        return new EmployeeBuildTest();
    }

    public EmployeeBuildTest setFirstName(String firstName){
        this.employee.setFirstName(firstName);
        return this;
    }

    public EmployeeBuildTest setLastName(String lastName){
        this.employee.setLastName(lastName);
        return this;
    }

    public EmployeeBuildTest setEmail(String email){
        this.employee.setEmail(email);
        return this;
    }

    public EmployeeBuildTest setNis(String nis){
        this.employee.setNis(nis);
        return this;
    }

    public Employee get(){
        return this.employee;
    }



}
