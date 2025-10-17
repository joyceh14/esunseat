package com.example.esunseat.controller;

import com.example.esunseat.dto.EmployeeDto;
import com.example.esunseat.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Find all the employees
    @GetMapping
    public List<EmployeeDto> listEmployees() {
        return employeeRepository.findAllDtos();
    }

    // Find specific employee
    @GetMapping("/{id}")
    public EmployeeDto getEmployee(@PathVariable String id) {
        return employeeRepository.findDtoById(id).orElseThrow();
    }
}
