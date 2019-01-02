package in.payroll.repository;

import in.payroll.domain.DAHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the DAHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DAHistoryRepository extends JpaRepository<DAHistory, Long> {
    Optional<DAHistory> findTopByOrderByDateDesc();
}
