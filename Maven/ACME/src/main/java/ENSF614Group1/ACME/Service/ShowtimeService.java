package ENSF614Group1.ACME.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ENSF614Group1.ACME.Model.Movie;
import ENSF614Group1.ACME.Model.Showtime;
import ENSF614Group1.ACME.Model.Theater;
import ENSF614Group1.ACME.Repository.ShowtimeRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ShowtimeService {

	@Autowired
	private ShowtimeRepository showtimeRepository;
	
	@Transactional
	public Showtime createShowtime(Showtime showtime) {
		return showtimeRepository.save(showtime);
	}
	
	public List<Showtime> getAllShowtimes(){
		return showtimeRepository.findAll();
	}
	
	public Showtime getShowtimeById(Long id) {
		Optional<Showtime> showtime = showtimeRepository.findById(id);
		if(showtime.isEmpty()) {
			throw new EntityNotFoundException("Showtime does not exist.");
		}
		return showtime.get();
	}
	
	public List<Showtime> getShowtimesInTheater(Theater theater){
		return showtimeRepository.findByTheater(theater);
	}
	
	@Transactional
	public Showtime updateShowtimeById(Long id, Showtime showtimeDetails) {
		Optional<Showtime> showtime = showtimeRepository.findById(id);
		if(showtime.isEmpty()) {
			throw new EntityNotFoundException("Showtime does not exist.");
		}
		Showtime s = showtime.get();
		s.setTimeOfDay(showtimeDetails.getTimeOfDay());
		return showtimeRepository.save(s);
	}
	
	@Transactional
	public void deleteShowtimeById(Long id) {
		if(!showtimeRepository.existsById(id)) {
			throw new EntityNotFoundException("Showtime does not exist.");
		}
		showtimeRepository.deleteById(id);
	}
} 
