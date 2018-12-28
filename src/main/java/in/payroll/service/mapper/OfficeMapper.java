package in.payroll.service.mapper;

import in.payroll.domain.*;
import in.payroll.service.dto.OfficeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Office and its DTO OfficeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OfficeMapper extends EntityMapper<OfficeDTO, Office> {


    @Mapping(target = "currentSalaries", ignore = true)
    Office toEntity(OfficeDTO officeDTO);

    default Office fromId(Long id) {
        if (id == null) {
            return null;
        }
        Office office = new Office();
        office.setId(id);
        return office;
    }
}
