package in.payroll.repository;

import in.payroll.domain.TAHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TAHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TAHistoryRepository extends JpaRepository<TAHistory, Long> {

}
