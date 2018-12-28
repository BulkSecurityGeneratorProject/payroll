package in.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Office.
 */
@Entity
@Table(name = "office")
public class Office implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "office_name")
    private String officeName;

    @Column(name = "city")
    private String city;

    @Column(name = "city_category")
    private String cityCategory;

    @Column(name = "pincode")
    private Long pincode;

    @Column(name = "taluka")
    private String taluka;

    @Column(name = "district")
    private String district;

    @OneToMany(mappedBy = "office")
    private Set<CurrentSalary> currentSalaries = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfficeName() {
        return officeName;
    }

    public Office officeName(String officeName) {
        this.officeName = officeName;
        return this;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getCity() {
        return city;
    }

    public Office city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCategory() {
        return cityCategory;
    }

    public Office cityCategory(String cityCategory) {
        this.cityCategory = cityCategory;
        return this;
    }

    public void setCityCategory(String cityCategory) {
        this.cityCategory = cityCategory;
    }

    public Long getPincode() {
        return pincode;
    }

    public Office pincode(Long pincode) {
        this.pincode = pincode;
        return this;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public String getTaluka() {
        return taluka;
    }

    public Office taluka(String taluka) {
        this.taluka = taluka;
        return this;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getDistrict() {
        return district;
    }

    public Office district(String district) {
        this.district = district;
        return this;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Set<CurrentSalary> getCurrentSalaries() {
        return currentSalaries;
    }

    public Office currentSalaries(Set<CurrentSalary> currentSalaries) {
        this.currentSalaries = currentSalaries;
        return this;
    }

    public Office addCurrentSalary(CurrentSalary currentSalary) {
        this.currentSalaries.add(currentSalary);
        currentSalary.setOffice(this);
        return this;
    }

    public Office removeCurrentSalary(CurrentSalary currentSalary) {
        this.currentSalaries.remove(currentSalary);
        currentSalary.setOffice(null);
        return this;
    }

    public void setCurrentSalaries(Set<CurrentSalary> currentSalaries) {
        this.currentSalaries = currentSalaries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Office office = (Office) o;
        if (office.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), office.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Office{" +
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
