package ENSF614Group1.ACME.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ENSF614Group1.ACME.Model.Movie;
import ENSF614Group1.ACME.Model.Theater;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	List<Movie> findByTheaters(Theater theater);
}
