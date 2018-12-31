package in.payroll.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TAHistory entity.
 */
public class TAHistoryDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private String cityCategory;

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

    public String getCityCategory() {
        return cityCategory;
    }

    public void setCityCategory(String cityCategory) {
        this.cityCategory = cityCategory;
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

        TAHistoryDTO tAHistoryDTO = (TAHistoryDTO) o;
        if (tAHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tAHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TAHistoryDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", cityCategory='" + getCityCategory() + "'" +
            ", currentValue=" + getCurrentValue() +
            "}";
    }
}
