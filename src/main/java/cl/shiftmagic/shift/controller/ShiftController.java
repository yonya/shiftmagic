package cl.shiftmagic.shift.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cl.shiftmagic.shift.model.Shift;
import cl.shiftmagic.shift.repository.ShiftRepository;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ShiftController {
	@Autowired
	ShiftRepository shiftRepository;
    
	@GetMapping("/shifts")
	public ResponseEntity<List<Shift>> getAllShifts(@RequestParam(required = false) String title) {
		try {
			List<Shift> shifts = new ArrayList<Shift>();
			if (title == null)
                shiftRepository.findAll().forEach(shifts::add);
			else
				shiftRepository.findByTitleContaining(title).forEach(shifts::add);
			if (shifts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(shifts, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/shifts/{id}")
	public ResponseEntity<Shift> getShiftById(@PathVariable("id") long id) {
		Optional<Shift> shiftData = shiftRepository.findById(id);
		if (shiftData.isPresent()) {
			return new ResponseEntity<>(shiftData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/shifts")
	public ResponseEntity<Shift> createTutorial(@RequestBody Shift shift) {
		try {
			Shift _shift = shiftRepository
					.save(new Shift(shift.getTitle(), shift.getDescription(), false));
			return new ResponseEntity<>(_shift, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/shifts/{id}")
	public ResponseEntity<Shift> updateShift(@PathVariable("id") long id, @RequestBody Shift shift) {
		Optional<Shift> shiftData = shiftRepository.findById(id);
		if (shiftData.isPresent()) {
			Shift _shift = shiftData.get();
			_shift.setTitle(shift.getTitle());
			_shift.setDescription(shift.getDescription());
			_shift.setPublished(shift.isPublished());
			return new ResponseEntity<>(shiftRepository.save(_shift), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/shifts/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			shiftRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/shifts")
	public ResponseEntity<HttpStatus> deleteAllShifts() {
		try {
			shiftRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/shifts/published")
	public ResponseEntity<List<Shift>> findByPublished() {
		try {
			List<Shift> shifts = shiftRepository.findByPublished(true);
			if (shifts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                
			}
			return new ResponseEntity<>(shifts, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}