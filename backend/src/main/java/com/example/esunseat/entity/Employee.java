package com.example.esunseat.entity;

import jakarta.persistence.*;


@Entity 
@Table(name="employee")
public class Employee {
	@Id 
	@Column(name="emp_id", length = 5, nullable = false)
	private String empId; 
	

	@Column(name = "name", length = 255, nullable = false)
	private String name;
	
	@Column(name = "email", length = 255, nullable = false)
	private String email; 
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="floor_seat_seq", 
				referencedColumnName = "floor_seat_seq",
				foreignKey = @ForeignKey(name = "fk_emp_seat")
	)
	private SeatingChart seat;
	
	// Getters and Setters 
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SeatingChart getSeat() {
		return seat;
	}

	public void setSeat(SeatingChart seat) {
		this.seat = seat;
	}
}
