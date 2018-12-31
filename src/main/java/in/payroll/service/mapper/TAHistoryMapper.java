package in.payroll.service.mapper;

import in.payroll.domain.*;
import in.payroll.service.dto.TAHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TAHistory and its DTO TAHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TAHistoryMapper extends EntityMapper<TAHistoryDTO, TAHistory> {



    default TAHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        TAHistory tAHistory = new TAHistory();
        tAHistory.setId(id);
        return tAHistory;
    }
}
