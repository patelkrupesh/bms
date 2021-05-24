package com.bms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "theaters")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class TheaterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="theatre_id")
	private long id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password; // ideally encrypted

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "city", nullable = false)
	private String city;

//	@OneToMany(mappedBy = "screens" , cascade = CascadeType.ALL)
//	@JsonIgnore
//	@Builder.Default
//	private List<ScreenEntity> screens = new ArrayList<>();

}