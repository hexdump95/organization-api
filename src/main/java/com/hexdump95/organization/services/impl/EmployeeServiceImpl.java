package com.hexdump95.organization.services.impl;

import com.hexdump95.organization.dtos.DepartmentDto;
import com.hexdump95.organization.dtos.EmployeeDto;
import com.hexdump95.organization.entities.Department;
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
    public List<EmployeeDto> get() {
        return repository.findAllAndMapToDto();
    }

    @Override
    public EmployeeDto getOne(Long id) {
        return repository.findById(id)
                .map(this::employeeToDto)
                .orElseThrow();
    }

    @Override
    public EmployeeDto post(EmployeeDto employeeDto) {
        Employee employee = dtoToEmployee(employeeDto);
        employee = repository.save(employee);
        return employeeToDto(employee);
    }

    @Override
    public EmployeeDto put(EmployeeDto employeeDto, Long id) {
        return repository.findById(id)
                .map(e -> dtoToEmployee(employeeDto))
                .map(repository::save).map(this::employeeToDto).orElseThrow();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private EmployeeDto employeeToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setPhone(employee.getPhone());

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(employee.getDepartment().getId());
        departmentDto.setName(employee.getDepartment().getName());

        employeeDto.setDepartment(departmentDto);
        return employeeDto;
    }

    private Employee dtoToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());

        Department department = new Department();
        department.setName(employeeDto.getDepartment().getName());

        employee.setDepartment(department);

        return employee;
    }

}
