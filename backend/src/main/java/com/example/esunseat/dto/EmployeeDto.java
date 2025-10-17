package com.example.esunseat.dto;

/**
 * Used to return a list or single employe info
 * and show the seat_no of that employee if assigned
 */

public record EmployeeDto(
		String empId, 
		String name, 
		String email, 
		Integer floorNo, 
		Integer seatNo
) {}
