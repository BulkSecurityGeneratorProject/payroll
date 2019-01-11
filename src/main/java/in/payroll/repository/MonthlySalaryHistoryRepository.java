package in.payroll.repository;

import in.payroll.domain.MonthlySalaryHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the MonthlySalaryHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlySalaryHistoryRepository extends JpaRepository<MonthlySalaryHistory, Long> {

    Optional<MonthlySalaryHistory> findOneByYearAndMonth(Integer year,Integer month);
}
