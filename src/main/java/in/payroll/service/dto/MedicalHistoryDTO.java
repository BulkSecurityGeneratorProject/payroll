package in.payroll.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MedicalHistory entity.
 */
public class MedicalHistoryDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private Long currentValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedicalHistoryDTO medicalHistoryDTO = (MedicalHistoryDTO) o;
        if (medicalHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalHistoryDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", currentValue=" + getCurrentValue() +
            "}";
    }
}
