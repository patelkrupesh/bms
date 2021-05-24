package com.bms.adaptors;

import com.bms.model.MovieEntity;
import com.bms.model.ShowEntity;
import com.bms.model.TheaterEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ShowUtil {

	public static Specification<ShowEntity> createShowSpecification(String movieName, String city, LocalDate date) {

		List<Specification<ShowEntity>> specifications = new ArrayList<>();

		if (movieName!= null && !movieName.isEmpty()) {
			specifications.add(getShowByMovieNameSpec(movieName));
		}
		if (city!= null && !city.isEmpty()) {
			specifications.add(getShowByCitySpec(city));
		}
		if (date != null) {
			specifications.add(getShowByDateSpec(date));
		}
		return createSpecification(specifications);
	}

	private static Specification<ShowEntity> createSpecification(List<Specification<ShowEntity>> specs) {
		Specification<ShowEntity> result = specs.get(0);
		for (int i = 1; i < specs.size(); i++) {
			result = Specification.where(result).and(specs.get(i));
		}
		return result;
	}

	private static Specification<ShowEntity> getShowByMovieNameSpec(String movieName) {
		return (root, query, criteriaBuilder) -> {
			Join<ShowEntity, MovieEntity> movieJoin = root.join("movie");
			return criteriaBuilder.equal(movieJoin.get("name"), movieName);
		};
	}

	private static Specification<ShowEntity> getShowByCitySpec(String city) {
		return (root, query, criteriaBuilder) -> {
			Join<ShowEntity, TheaterEntity> theaterJoin = root.join("screen").join("theater");
			return criteriaBuilder.equal(theaterJoin.get("city"), city);
		};
	}

	private static Specification<ShowEntity> getShowByDateSpec(LocalDate showDate) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), showDate);
	}
}