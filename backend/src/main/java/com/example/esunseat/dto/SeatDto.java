package com.example.esunseat.dto;

/**
 * Used to display the status of every seat in the frontend 
 * including: floor_no, seat_no, and employe_id
 */

public record SeatDto(
		Integer floorSeatSeq, 
		Integer floorNo, 
		Integer seatNo, 
		String empId, 
		String empName, 
		String empEmail
) {}
