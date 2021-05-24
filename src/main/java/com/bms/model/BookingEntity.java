package com.bms.model;

import com.bms.enums.BookingStatus;
import com.bms.enums.Language;
import com.bms.enums.MovieGenre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movies")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class BookingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="booking_id")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "booking_time")
	private Date bookingtime;

	@Column(name = "alloted_seats", nullable = false)
	private String allottedSeats;

	@Column(name = "amount", nullable = false)
	private double amount;

	@Column(name = "ticketDownloadlink", nullable = false)
	private String ticketDownloadlink;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = true)
	private BookingStatus status;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "show_id")
	@JsonIgnore
	private ShowEntity show;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username")
	@JsonIgnore
	private UserEntity user;
}