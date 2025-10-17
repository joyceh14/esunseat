package com.example.esunseat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * frontend passed: the empId that is moving seat + new floorNo and seatNo
 */

public record MoveSeatRequest(
		@NotBlank(message = "empId cannot be null") String empId, 
		@NotNull(message = "toFloorNo cannot be null") Integer toFloorNo, 
		@NotNull(message = "toSeatNo cannot be null") Integer toSeatNo
) {}
