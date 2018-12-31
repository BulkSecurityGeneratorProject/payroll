package in.payroll.repository;

import in.payroll.domain.Office;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Office entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {

}
