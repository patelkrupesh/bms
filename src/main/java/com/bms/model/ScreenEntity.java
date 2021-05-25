package com.bms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "screens")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ScreenEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="screen_id")
	private long id;

	@Column(name = "screenname", nullable = false)
	private String screenname;

	@Column(name = "noofseats", nullable = false)
	private String noOfSeats;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "theatre_id")
	@JsonIgnore
	private TheaterEntity theater;

}