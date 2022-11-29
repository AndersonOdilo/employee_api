package com.vasconcelos.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@PrimaryKeyJoinColumn(name = "id_person")
public class Employee extends Person {

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "NIS is required")
    @Pattern(regexp = "[0-9]+$", message = "Nis is allow only numbers")
    @Column(nullable = false, unique = true)
    private String nis;

    public Employee() {
    }

    public Employee(UUID id, String firstName, String lastName, LocalDateTime created, LocalDateTime update, String email, String nis) {
        super(id, firstName, lastName, created, update);
        this.email = email;
        this.nis = nis;
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
