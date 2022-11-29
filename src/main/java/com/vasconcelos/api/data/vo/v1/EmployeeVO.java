package com.vasconcelos.api.data.vo.v1;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

public class EmployeeVO extends RepresentationModel<EmployeeVO> implements Serializable {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String nis;

    public EmployeeVO() {
    }

    public EmployeeVO(UUID id, String firstName, String lastName, String email, String nis) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.nis = nis;
    }

    public UUID getId() {
        return id;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }
}

