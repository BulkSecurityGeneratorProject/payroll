package in.payroll.repository;

import in.payroll.domain.HRAHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HRAHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HRAHistoryRepository extends JpaRepository<HRAHistory, Long> {

}
