package com.bms.model;

import com.bms.enums.Language;
import com.bms.enums.MovieGenre;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "movies")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class MovieEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="movie_id")
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "language", nullable = false)
	private Language language;

	@Column(name = "length", nullable = false)
	private Integer length;

	@Column(name = "starcast", nullable = true)
	private String starcast;

	@Enumerated(EnumType.STRING)
	@Column(name = "genre", nullable = true)
	private MovieGenre genre;

	@Column(name = "release_date", columnDefinition = "DATE", nullable = false)
	private LocalDate releaseDate;
}