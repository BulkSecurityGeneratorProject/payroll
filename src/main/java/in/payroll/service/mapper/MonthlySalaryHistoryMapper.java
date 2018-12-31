package in.payroll.service.mapper;

import in.payroll.domain.*;
import in.payroll.service.dto.MonthlySalaryHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MonthlySalaryHistory and its DTO MonthlySalaryHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {FacultyMapper.class})
public interface MonthlySalaryHistoryMapper extends EntityMapper<MonthlySalaryHistoryDTO, MonthlySalaryHistory> {

    @Mapping(source = "faculty.id", target = "facultyId")
    @Mapping(source = "faculty.firstName", target = "facultyFirstName")
    MonthlySalaryHistoryDTO toDto(MonthlySalaryHistory monthlySalaryHistory);

    @Mapping(source = "facultyId", target = "faculty")
    MonthlySalaryHistory toEntity(MonthlySalaryHistoryDTO monthlySalaryHistoryDTO);

    default MonthlySalaryHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        MonthlySalaryHistory monthlySalaryHistory = new MonthlySalaryHistory();
        monthlySalaryHistory.setId(id);
        return monthlySalaryHistory;
    }
}
