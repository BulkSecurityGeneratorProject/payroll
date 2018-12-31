package in.payroll.service.mapper;

import in.payroll.domain.*;
import in.payroll.service.dto.MedicalHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MedicalHistory and its DTO MedicalHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedicalHistoryMapper extends EntityMapper<MedicalHistoryDTO, MedicalHistory> {



    default MedicalHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setId(id);
        return medicalHistory;
    }
}
