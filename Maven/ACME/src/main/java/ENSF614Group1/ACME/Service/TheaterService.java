package ENSF614Group1.ACME.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Repository.TheaterRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TheaterService {

	@Autowired
	private TheaterRepository theaterRepository;
	
	@Transactional
	public Theater createTheater(Theater theater) {
		return theaterRepository.save(theater);
	}
	
	public List<Theater> getAllTheaters(){
		return theaterRepository.findAll();
	}
	
	public Optional<Theater> getTheaterById(Long id){
		return theaterRepository.findById(id);
	}
	
	@Transactional
	public Theater updateTheater(Long id, Theater theaterDetails) {
		Optional<Theater> theater = theaterRepository.findById(id);
		if (theater.isEmpty()) {
			throw new EntityNotFoundException("Theater does not exist.");
		}
		Theater t = theater.get();
		t.setName(theaterDetails.getName());
		t.setSeats(theaterDetails.getSeats());
		t.setShowtimes(theaterDetails.getShowtimes());
		return theaterRepository.save(t);
	}
	
	@Transactional
	public void deleteTheater(Long id) {
		if (!theaterRepository.existsById(id)) {
			throw new EntityNotFoundException("Theater does not exist.");
		}
		theaterRepository.deleteById(id);
	}
}
