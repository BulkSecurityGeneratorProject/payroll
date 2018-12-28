package in.payroll.repository;

import in.payroll.domain.MonthlySalaryHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MonthlySalaryHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlySalaryHistoryRepository extends JpaRepository<MonthlySalaryHistory, Long> {

}
