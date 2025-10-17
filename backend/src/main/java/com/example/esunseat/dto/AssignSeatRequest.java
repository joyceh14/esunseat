package com.example.esunseat.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * frontend passed in: empId + floorNo, seatNo
 * used to fist assignment or chossing seat 
 */

public record AssignSeatRequest(
		@NotBlank(message = "empId cannot be null") String empId, 
		@NotNull(message = "floorNo cannot be null") Integer floorNo, 
		@NotNull(message = "seatNo cannot be null") Integer seatNo
) {}
