package com.bms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class UserEntity {

	@Id
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password; // ideally encrypted

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "type", nullable = false)
	private String type;

//	@OneToMany(mappedBy = "bookings")
//	@JsonIgnore
//	private List<BookingEntity> bookingEntities;
//
//	@OneToMany(mappedBy = "payments")
//	@JsonIgnore
//	private List<PaymentEntity> paymentEntities;
}