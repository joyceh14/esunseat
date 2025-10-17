package com.example.esunseat.controller;

import com.example.esunseat.dto.AssignSeatRequest;
import com.example.esunseat.dto.MoveSeatRequest;
import com.example.esunseat.dto.SeatDto;
import com.example.esunseat.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/seats")
public class SeatController {
	private final SeatService seatService; 
	
	public SeatController(SeatService seatService) {
		this.seatService = seatService;
	}
	
	// Get the list of all seats 
	@GetMapping 
	public List<SeatDto> listSeats() {
		return seatService.listAllSeats();
	}
	
	// Get specific seat 
	@GetMapping("/{id}")
	public SeatDto getSeat(@PathVariable Integer id) {
		return seatService.getSeat(id);
	}
	
	// Assign the seat 
	@PostMapping("/assign")
	public ResponseEntity<SeatDto> assignSeat(@Valid @RequestBody AssignSeatRequest req) {
		return ResponseEntity.ok(seatService.assignSeat(req));
	}
	
	// Change the seat 
	@PostMapping("/move")
	public ResponseEntity<SeatDto> moveSeat(@Valid @RequestBody MoveSeatRequest req) {
		return ResponseEntity.ok(seatService.moveSeat(req));
	}
	
	// Clear the seat 
	@DeleteMapping("/{id}/release")
	public ResponseEntity<Void> releaseSeat(@PathVariable Integer id) {
		seatService.releaseSeat(id);
		return ResponseEntity.noContent().build();
	}
}
