package in.payroll.service.mapper;

import in.payroll.domain.*;
import in.payroll.service.dto.CLAHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CLAHistory and its DTO CLAHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CLAHistoryMapper extends EntityMapper<CLAHistoryDTO, CLAHistory> {



    default CLAHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        CLAHistory cLAHistory = new CLAHistory();
        cLAHistory.setId(id);
        return cLAHistory;
    }
}
