package in.payroll.repository;

import in.payroll.domain.HRAHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the HRAHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HRAHistoryRepository extends JpaRepository<HRAHistory, Long> {

    Optional<HRAHistory> findOneByCityCategoryOrderByDateDesc(String cityCategory);
}
