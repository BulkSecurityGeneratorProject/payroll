package in.payroll.service.mapper;

import in.payroll.domain.*;
import in.payroll.service.dto.CurrentSalaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CurrentSalary and its DTO CurrentSalaryDTO.
 */
@Mapper(componentModel = "spring", uses = {OfficeMapper.class})
public interface CurrentSalaryMapper extends EntityMapper<CurrentSalaryDTO, CurrentSalary> {

    @Mapping(source = "office.id", target = "officeId")
    @Mapping(source = "office.officeName", target = "officeOfficeName")
    CurrentSalaryDTO toDto(CurrentSalary currentSalary);

    @Mapping(target = "faculty", ignore = true)
    @Mapping(source = "officeId", target = "office")
    CurrentSalary toEntity(CurrentSalaryDTO currentSalaryDTO);

    default CurrentSalary fromId(Long id) {
        if (id == null) {
            return null;
        }
        CurrentSalary currentSalary = new CurrentSalary();
        currentSalary.setId(id);
        return currentSalary;
    }
}
