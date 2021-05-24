package com.bms.dto;

import com.bms.enums.SeatCategory;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class BookTicketInputDto {

	@NotEmpty(message = "Select atleast 1 seat")
	private Set<Long> seatIds;

	@NotNull(message = "User id cannot be null")
	private String username;

	@Min(value = 1, message = "Show id is Invalid")
	private long showId;

	@Min(value = 1, message = "Amount is Invalid")
	private double amount;

}