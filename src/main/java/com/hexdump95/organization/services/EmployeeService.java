package com.hexdump95.organization.services;

import com.hexdump95.organization.entities.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> get();
    Employee getOne(Long id);
    Employee post(Employee employee);
    Employee put(Employee employee, Long id);
    void delete(Long id);
}
