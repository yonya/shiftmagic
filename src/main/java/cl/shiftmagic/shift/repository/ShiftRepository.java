package cl.shiftmagic.shift.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.shiftmagic.shift.model.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
  List<Shift> findByPublished(boolean published);
  List<Shift> findByTitleContaining(String title);
}
