package com.bms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movieshows")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ShowEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="show_id")
	private long id;

	@Column(name = "release_date", columnDefinition = "DATE", nullable = false)
	private LocalDate releaseDate;

	@Column(name = "show_time", nullable = false)
	private int time;

//	@OneToMany(mappedBy = "bookings")
//	@JsonIgnore
//	private List<BookingEntity> bookingEntities;

//	@OneToMany(mappedBy = "seats")
//	@JsonIgnore
//	private List<SeatsEntity> seats;

	@ManyToOne
	@JoinColumn(name = "screen_id")
	@JsonIgnore
	private ScreenEntity screenEntity;
}