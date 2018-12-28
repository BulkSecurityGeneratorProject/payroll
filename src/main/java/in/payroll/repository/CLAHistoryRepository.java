package in.payroll.repository;

import in.payroll.domain.CLAHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CLAHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CLAHistoryRepository extends JpaRepository<CLAHistory, Long> {

}
