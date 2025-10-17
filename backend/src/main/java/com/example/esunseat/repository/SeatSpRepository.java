package com.example.esunseat.repository;

import com.example.esunseat.dto.SeatDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SeatSpRepository {

    private final JdbcTemplate jdbc;

    public SeatSpRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void assignSeat(String empId, int floorNo, int seatNo) {
        jdbc.update("CALL AssignSeat(?, ?, ?)", empId, floorNo, seatNo);
    }

    public void releaseSeat(int floorSeatSeq) {
        jdbc.update("CALL ReleaseSeat(?)", floorSeatSeq);
    }
    
    public SeatDto moveSeat(String empId, int toFloorNo, int toSeatNo) {
        return jdbc.query("CALL MoveSeat(?, ?, ?)",
                ps -> {
                    ps.setString(1, empId);
                    ps.setInt(2, toFloorNo);
                    ps.setInt(3, toSeatNo);
                },
                rs -> rs.next()
                        ? new SeatDto(
                            rs.getInt("floor_seat_seq"),
                            rs.getInt("floor_no"),
                            rs.getInt("seat_no"),
                            rs.getString("emp_id"),
                            rs.getString("name"),
                            rs.getString("email")
                        )
                        : null
        );
    }
}