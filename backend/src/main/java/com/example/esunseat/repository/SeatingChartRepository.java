package com.example.esunseat.repository;

import com.example.esunseat.entity.SeatingChart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SeatingChartRepository extends JpaRepository<SeatingChart, Integer> {
	// Find all seats on a specific floor, ordered by seat number (ascending)
    List<SeatingChart> findByFloorNoOrderBySeatNoAsc(Integer floorNo);
    
 // Find a specific seat by floor number and seat number (used for assigning or moving seats)
    Optional<SeatingChart> findByFloorNoAndSeatNo(Integer floorNo, Integer seatNo);

    // Find all available (unassigned) seats
    List<SeatingChart> findAllByEmployeeIsNull();

    // Find the current seat assigned to a specific employee
    Optional<SeatingChart> findByEmployeeEmpId(String empId);
}