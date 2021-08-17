package com.hexdump95.organization.controllers;

import com.hexdump95.organization.dtos.EmployeeDto;
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

    @SecurityRequirement(name = "bearer-key")
    @GetMapping
    public List<EmployeeDto> get() {
        return service.get();
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping(path = "/{id}")
    public ResponseEntity<EmployeeDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    public ResponseEntity<EmployeeDto> post(@RequestBody EmployeeDto employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.post(employee));
    }

    @SecurityRequirement(name = "bearer-key")
    @PutMapping(path = "/{id}")
    public ResponseEntity<EmployeeDto> put(@RequestBody EmployeeDto employee, @PathVariable Long id) {
        return ResponseEntity.ok(service.put(employee, id));
    }

    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
