package in.payroll.service.mapper;

import in.payroll.domain.*;
import in.payroll.service.dto.HRAHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HRAHistory and its DTO HRAHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HRAHistoryMapper extends EntityMapper<HRAHistoryDTO, HRAHistory> {



    default HRAHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        HRAHistory hRAHistory = new HRAHistory();
        hRAHistory.setId(id);
        return hRAHistory;
    }
}
