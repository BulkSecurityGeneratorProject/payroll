package in.payroll.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CurrentSalary entity.
 */
public class CurrentSalaryDTO implements Serializable {

    private Long id;

    private Long basicPay;

    private Long gradePay;

    private String cityCategory;

    private Long officeId;

    private String officeOfficeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBasicPay() {
        return basicPay;
    }

    public void setBasicPay(Long basicPay) {
        this.basicPay = basicPay;
    }

    public Long getGradePay() {
        return gradePay;
    }

    public void setGradePay(Long gradePay) {
        this.gradePay = gradePay;
    }

    public String getCityCategory() {
        return cityCategory;
    }

    public void setCityCategory(String cityCategory) {
        this.cityCategory = cityCategory;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getOfficeOfficeName() {
        return officeOfficeName;
    }

    public void setOfficeOfficeName(String officeOfficeName) {
        this.officeOfficeName = officeOfficeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurrentSalaryDTO currentSalaryDTO = (CurrentSalaryDTO) o;
        if (currentSalaryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), currentSalaryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurrentSalaryDTO{" +
            "id=" + getId() +
            ", basicPay=" + getBasicPay() +
            ", gradePay=" + getGradePay() +
            ", cityCategory='" + getCityCategory() + "'" +
            ", office=" + getOfficeId() +
            ", office='" + getOfficeOfficeName() + "'" +
            "}";
    }
}
