package com.vasconcelos.api.repository;

import com.vasconcelos.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByEmail(String email);

    boolean existsByNis(String nis);

    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByNisAndIdNot(String nis, UUID id);


}
