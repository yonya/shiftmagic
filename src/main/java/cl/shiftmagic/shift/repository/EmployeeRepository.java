package cl.shiftmagic.shift.repository;
import org.springframework.data.repository.CrudRepository;

import cl.shiftmagic.shift.model.*;


public interface EmployeeRepository extends CrudRepository<Employee, Long> { 

}