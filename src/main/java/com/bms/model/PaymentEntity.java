package com.bms.model;

import com.bms.enums.Language;
import com.bms.enums.MovieGenre;
import com.bms.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "payments")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class PaymentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="payment_id")
	private long id;

	@Column(name = "amount", nullable = false)
	private Integer name;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = true)
	private PaymentStatus status;

	@Column(name = "gateway", nullable = false)
	private String gateway;

//	@ManyToOne
//	@JoinColumn(name = "username")
//	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "booking_id")
	@JsonIgnore
	private BookingEntity bookingEntity;

}