package ENSF614Group1.ACME.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ENSF614Group1.ACME.Model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
