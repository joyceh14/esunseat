package com.example.esunseat.service.impl;

import com.example.esunseat.dto.AssignSeatRequest;
import com.example.esunseat.dto.MoveSeatRequest;
import com.example.esunseat.dto.SeatDto;
import com.example.esunseat.entity.SeatingChart;
import com.example.esunseat.repository.SeatingChartRepository;
import com.example.esunseat.repository.SeatSpRepository;
import com.example.esunseat.service.SeatService;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SeatServiceImpl implements SeatService {

    private final SeatingChartRepository seatingChartRepository;
    private final SeatSpRepository seatSpRepository;

    public SeatServiceImpl(SeatingChartRepository seatingChartRepository,
            SeatSpRepository seatSpRepository) {
		this.seatingChartRepository = seatingChartRepository;
		this.seatSpRepository = seatSpRepository;
	}

    // Use JPA to search all the seats 
    @Override
    public List<SeatDto> listAllSeats() {
        return seatingChartRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public SeatDto getSeat(Integer floorSeatSeq) {
        SeatingChart seat = seatingChartRepository.findById(floorSeatSeq)
                .orElseThrow(() -> new RuntimeException("Seat not found: " + floorSeatSeq));
        return toDto(seat);
    }

    // Using Stored Procedure to call assignSeat
    @Override
    public SeatDto assignSeat(AssignSeatRequest request) {
        try {
            seatSpRepository.assignSeat(request.empId(), request.floorNo(), request.seatNo());
            var seat = seatingChartRepository.findByFloorNoAndSeatNo(request.floorNo(), request.seatNo())
                    .orElseThrow(() -> new RuntimeException("Seat not found after SP call"));
            return toDto(seat);
        } catch (DataAccessException ex) {
            throw new RuntimeException("AssignSeat failed: " + ex.getMostSpecificCause().getMessage(), ex);
        }
    }

 // Using Stored Procedure to call moveSeat
    @Override
    public SeatDto moveSeat(MoveSeatRequest request) {
        try {
            return seatSpRepository.moveSeat(
                    request.empId(),
                    request.toFloorNo(),
                    request.toSeatNo()
            );
        } catch (DataAccessException ex) {
            throw new RuntimeException("MoveSeat failed: " + ex.getMostSpecificCause().getMessage(), ex);
        }
    }

    // Using Stored Procedure to call releaseSeat 
    @Override
    public void releaseSeat(Integer floorSeatSeq) {
        try {
            seatSpRepository.releaseSeat(floorSeatSeq);
        } catch (DataAccessException ex) {
            throw new RuntimeException("ReleaseSeat failed: " + ex.getMostSpecificCause().getMessage(), ex);
        }
    }

    private SeatDto toDto(SeatingChart seat) {
        return new SeatDto(
                seat.getFloorSeatSeq(),
                seat.getFloorNo(),
                seat.getSeatNo(),
                seat.getEmployee() == null ? null : seat.getEmployee().getEmpId(),
                seat.getEmployee() == null ? null : seat.getEmployee().getName(),
                seat.getEmployee() == null ? null : seat.getEmployee().getEmail()
        );
    }
}
