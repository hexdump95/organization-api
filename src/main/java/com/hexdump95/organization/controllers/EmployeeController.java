package com.hexdump95.organization.controllers;

import com.hexdump95.organization.entities.Employee;
import com.hexdump95.organization.services.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO
@Tag(name = "employees")
@RestController
@RequestMapping(value = EmployeeController.EMPLOYEE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {
    public static final String EMPLOYEE_ENDPOINT = "/api/v1/employees";
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> get() {
        return service.get();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Employee> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    @PutMapping(path = "/{id}")
    public ResponseEntity<Employee> put(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.post(employee));
    }

    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
