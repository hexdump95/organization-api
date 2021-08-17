package com.hexdump95.organization.repositories;

import com.hexdump95.organization.dtos.EmployeeDto;
import com.hexdump95.organization.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT new com.hexdump95.organization.dtos" +
            ".EmployeeDto(e.id, e.firstName, e.lastName) " +
            "FROM Employee AS e")
    List<EmployeeDto> findAllAndMapToDto();
}
