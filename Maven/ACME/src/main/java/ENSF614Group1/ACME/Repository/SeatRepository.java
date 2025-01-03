package ENSF614Group1.ACME.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ENSF614Group1.ACME.Model.Seat;
import ENSF614Group1.ACME.Model.Showtime;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
	public List<Seat> findSeatsByShowtime(Showtime showtime);
}
