package com.vasconcelos.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 30, message = "First name must be beteween 2 to 30 characters")
    @Column(length = 30, nullable = false)
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be beteween 2 to 50 characters")
    @Column(length = 50, nullable = false)
    private String lastName;

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime update;

    public Person() {
    }

    public Person(UUID id, String firstName, String lastName, LocalDateTime created, LocalDateTime update) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.created = created;
        this.update = update;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdate() {
        return update;
    }

    public void setUpdate(LocalDateTime update) {
        this.update = update;
    }
}
