package com.bms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "bookingexpiry")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class BookingExpiryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="booking_expiry_id")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiry_time")
	private Date expirytime;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "booking_id")
	@JsonIgnore
	private BookingEntity booking;
}