package ENSF614Group1.ACME.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ENSF614Group1.ACME.Model.Showtime;
import ENSF614Group1.ACME.Model.Theater;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
	List<Showtime> findByTheater(Theater theater);
}
