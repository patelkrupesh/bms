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

	@Column(name = "date", columnDefinition = "DATE", nullable = false)
	private LocalDate date;

	@Column(name = "show_time", nullable = false)
	private int time;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "movie_id")
	@JsonIgnore
	private MovieEntity movie;

//	@OneToMany(mappedBy = "seats")
//	@JsonIgnore
//	private List<SeatsEntity> seats;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "screen_id")
	@JsonIgnore
	private ScreenEntity screen;
}