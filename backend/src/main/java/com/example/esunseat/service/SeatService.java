package com.example.esunseat.service;

import com.example.esunseat.dto.AssignSeatRequest;
import com.example.esunseat.dto.MoveSeatRequest;
import com.example.esunseat.dto.SeatDto;

import java.util.List;

public interface SeatService {
	// Get the list of all seats (including employee info) 
	List<SeatDto> listAllSeats();
	
	// Get detail info of single seat 
	SeatDto getSeat(Integer floorSeatSeq);
	
	// Assign employee to seat 
	SeatDto assignSeat(AssignSeatRequest request);
	
	// Employee change seat 
	SeatDto moveSeat(MoveSeatRequest request);
	
	// Release seat 
	void releaseSeat(Integer floorSeatSeq);
}
