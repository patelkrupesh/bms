package com.bms.model;

import com.bms.enums.SeatCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "seats")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class SeatsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="seat_id")
	private long id;

	@Column(name = "seat_number", nullable = false)
	private String seatNumber;

	@Column(name = "rate", nullable = false)
	private int rate;

	@Enumerated(EnumType.STRING)
	@Column(name = "seat_type", nullable = false)
	private SeatCategory seatType;

	@Column(name = "is_booked", columnDefinition = "bit(1) default 0", nullable = false)
	private boolean booked;

	@ManyToOne
	@JoinColumn(name = "show_id")
	@JsonIgnore
	private ScreenEntity screenEntity;

	@ManyToOne
	@JoinColumn(name = "show_id")
	@JsonIgnore
	private ShowEntity showEntity;

	@ManyToOne
	@JoinColumn(name = "booking_id")
	@JsonIgnore
	private BookingEntity bookingEntity;
}