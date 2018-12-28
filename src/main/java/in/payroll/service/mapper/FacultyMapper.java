package in.payroll.service.mapper;

import in.payroll.domain.*;
import in.payroll.service.dto.FacultyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Faculty and its DTO FacultyDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CurrentSalaryMapper.class})
public interface FacultyMapper extends EntityMapper<FacultyDTO, Faculty> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "currentSalary.id", target = "currentSalaryId")
    FacultyDTO toDto(Faculty faculty);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "currentSalaryId", target = "currentSalary")
    @Mapping(target = "monthlySalaryHistories", ignore = true)
    Faculty toEntity(FacultyDTO facultyDTO);

    default Faculty fromId(Long id) {
        if (id == null) {
            return null;
        }
        Faculty faculty = new Faculty();
        faculty.setId(id);
        return faculty;
    }
}
