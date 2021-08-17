package com.hexdump95.organization.services;

import com.hexdump95.organization.dtos.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> get();
    EmployeeDto getOne(Long id);
    EmployeeDto post(EmployeeDto employee);
    EmployeeDto put(EmployeeDto employee, Long id);
    void delete(Long id);
}
