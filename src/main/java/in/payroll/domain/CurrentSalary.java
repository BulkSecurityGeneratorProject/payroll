package in.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CurrentSalary.
 */
@Entity
@Table(name = "current_salary")
public class CurrentSalary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "basic_pay")
    private Long basicPay;

    @Column(name = "grade_pay")
    private Long gradePay;

    @Column(name = "city_category")
    private String cityCategory;

    @OneToOne(mappedBy = "currentSalary")
    @JsonIgnore
    private Faculty faculty;

    @ManyToOne
    @JsonIgnoreProperties("currentSalaries")
    private Office office;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBasicPay() {
        return basicPay;
    }

    public CurrentSalary basicPay(Long basicPay) {
        this.basicPay = basicPay;
        return this;
    }

    public void setBasicPay(Long basicPay) {
        this.basicPay = basicPay;
    }

    public Long getGradePay() {
        return gradePay;
    }

    public CurrentSalary gradePay(Long gradePay) {
        this.gradePay = gradePay;
        return this;
    }

    public void setGradePay(Long gradePay) {
        this.gradePay = gradePay;
    }

    public String getCityCategory() {
        return cityCategory;
    }

    public CurrentSalary cityCategory(String cityCategory) {
        this.cityCategory = cityCategory;
        return this;
    }

    public void setCityCategory(String cityCategory) {
        this.cityCategory = cityCategory;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public CurrentSalary faculty(Faculty faculty) {
        this.faculty = faculty;
        return this;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Office getOffice() {
        return office;
    }

    public CurrentSalary office(Office office) {
        this.office = office;
        return this;
    }

    public void setOffice(Office office) {
        this.office = office;
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
        CurrentSalary currentSalary = (CurrentSalary) o;
        if (currentSalary.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), currentSalary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurrentSalary{" +
            "id=" + getId() +
            ", basicPay=" + getBasicPay() +
            ", gradePay=" + getGradePay() +
            ", cityCategory='" + getCityCategory() + "'" +
            "}";
    }
}
