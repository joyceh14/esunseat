package com.example.esunseat.repository;

import com.example.esunseat.dto.EmployeeDto;
import com.example.esunseat.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	// Find an employee by email (SELECT * FROM employee WHERE email = ?)
    Optional<Employee> findByEmail(String email);

    // Find an employee by name (SELECT * FROM employee WHERE name = ?)
    Optional<Employee> findByName(String name);

    // Check if an employee has already been assigned a seat (SELECT COUNT(*) > 0 FROM employee WHERE emp_id = ? AND floor_seat_seq IS NOT NULL)
    boolean existsByEmpIdAndSeatIsNotNull(String empId);
    
    @Query("""
            select new com.example.esunseat.dto.EmployeeDto(
                e.empId, e.name, e.email,
                sc.floorNo, sc.seatNo
            )
            from Employee e
            left join e.seat sc
            order by e.empId
            """)
     List<EmployeeDto> findAllDtos();

     @Query("""
            select new com.example.esunseat.dto.EmployeeDto(
                e.empId, e.name, e.email,
                sc.floorNo, sc.seatNo
            )
            from Employee e
            left join e.seat sc
            where e.empId = :id
            """)
     Optional<EmployeeDto> findDtoById(String id);
}