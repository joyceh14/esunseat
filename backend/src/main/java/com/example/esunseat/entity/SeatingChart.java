package com.example.esunseat.entity;

import jakarta.persistence.*;

@Entity 
@Table(name="seating_chart")
public class SeatingChart {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "floor_seat_seq", nullable = false)
	private Integer floorSeatSeq;
	
	@Column(name="floor_no", nullable = false)
	private Integer floorNo;
	
	@Column(name="seat_no", nullable = false)
	private Integer seatNo; 
	
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(
			name = "emp_id", 
			referencedColumnName = "emp_id", 
			foreignKey = @ForeignKey(name = "fk_seat_emp")
	)
	private Employee employee;
	
	// Getters and Setters 
	public Integer getFloorSeatSeq() {
		return floorSeatSeq;
	}

	public void setFloorSeatSeq(Integer floorSeatSeq) {
		this.floorSeatSeq = floorSeatSeq;
	}

	public Integer getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(Integer floorNo) {
		this.floorNo = floorNo;
	}

	public Integer getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(Integer seatNo) {
		this.seatNo = seatNo;
	}
	
	public Employee getEmployee() {
		return employee; 
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
