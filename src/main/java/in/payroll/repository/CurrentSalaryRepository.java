package in.payroll.repository;

import in.payroll.domain.CurrentSalary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CurrentSalary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrentSalaryRepository extends JpaRepository<CurrentSalary, Long> {

}
