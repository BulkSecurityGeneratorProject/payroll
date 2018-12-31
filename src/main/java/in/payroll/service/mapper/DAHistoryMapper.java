package in.payroll.service.mapper;

import in.payroll.domain.*;
import in.payroll.service.dto.DAHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DAHistory and its DTO DAHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DAHistoryMapper extends EntityMapper<DAHistoryDTO, DAHistory> {



    default DAHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        DAHistory dAHistory = new DAHistory();
        dAHistory.setId(id);
        return dAHistory;
    }
}
