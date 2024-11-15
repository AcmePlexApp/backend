package ENSF614Group1.ACME.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ENSF614Group1.ACME.Model.Seat;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Repository.SeatRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SeatService {

	@Autowired
	private SeatRepository seatRepository;
	
	@Transactional
	public Seat createSeat(Seat seat) {
		return seatRepository.save(seat);
	}
	
	public List<Seat> getAllSeats(){
		return seatRepository.findAll();
	}
	
	public Seat getSeatById(Long id){
		Optional<Seat> seat = seatRepository.findById(id);
		if(seat.isEmpty()) {
			throw new EntityNotFoundException("Seat does not exist.");
		}
		return seat.get();
	}
	
	public List<Seat> getSeatsInTheater(Theater theater){
		return seatRepository.findByTheater(theater);
	}
	
	@Transactional
	public Seat updateSeat(Long id, Seat seatDetails) {
		Optional<Seat> seat = seatRepository.findById(id);
		if (seat.isEmpty()) {
			throw new EntityNotFoundException("Seat does not exist.");
		}
		Seat s = seat.get();
		s.setRow(seatDetails.getRow());
		s.setSeatNumber(seatDetails.getSeatNumber());
		return seatRepository.save(s);
	}
	
	@Transactional
	public void deleteSeat(Long id) {
		if (!seatRepository.existsById(id)) {
			throw new EntityNotFoundException("Seat does not exist.");
		}
		seatRepository.deleteById(id);
	}
}
