package in.payroll.repository;

import in.payroll.domain.CLAHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the CLAHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CLAHistoryRepository extends JpaRepository<CLAHistory, Long> {
    Optional<CLAHistory> findOneByCityCategoryOrderByDateDesc(String cityCategory);
}
