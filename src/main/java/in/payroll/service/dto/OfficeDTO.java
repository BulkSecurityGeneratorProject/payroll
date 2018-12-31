package in.payroll.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Office entity.
 */
public class OfficeDTO implements Serializable {

    private Long id;

    private String officeName;

    private String city;

    private String cityCategory;

    private Long pincode;

    private String taluka;

    private String district;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCategory() {
        return cityCategory;
    }

    public void setCityCategory(String cityCategory) {
        this.cityCategory = cityCategory;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfficeDTO officeDTO = (OfficeDTO) o;
        if (officeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), officeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OfficeDTO{" +
            "id=" + getId() +
            ", officeName='" + getOfficeName() + "'" +
            ", city='" + getCity() + "'" +
            ", cityCategory='" + getCityCategory() + "'" +
            ", pincode=" + getPincode() +
            ", taluka='" + getTaluka() + "'" +
            ", district='" + getDistrict() + "'" +
            "}";
    }
}
