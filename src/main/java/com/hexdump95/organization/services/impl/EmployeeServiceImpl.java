package com.hexdump95.organization.services.impl;

import com.hexdump95.organization.entities.Employee;
import com.hexdump95.organization.repositories.EmployeeRepository;
import com.hexdump95.organization.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> get() {
        return repository.findAll();
    }

    @Override
    public Employee getOne(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    @Override
    public Employee post(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee put(Employee employee, Long id) {
        return repository.findById(id)
                .map(e -> {
                    e.setFirstName(employee.getFirstName());
                    e.setLastName(employee.getLastName());
                    e.setEmail(employee.getEmail());
                    e.setPhone(employee.getPhone());
                    e.setDepartament(employee.getDepartament());
                    return e;
                }).map(repository::save).orElseThrow();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
