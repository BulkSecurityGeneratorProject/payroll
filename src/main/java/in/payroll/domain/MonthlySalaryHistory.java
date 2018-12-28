package in.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MonthlySalaryHistory.
 */
@Entity
@Table(name = "monthly_salary_history")
public class MonthlySalaryHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "office_name")
    private String officeName;

    @Column(name = "basic_pay")
    private Long basicPay;

    @Column(name = "grade_pay")
    private Long gradePay;

    @Column(name = "basic_total")
    private Long basicTotal;

    @Column(name = "da_percent")
    private Long daPercent;

    @Column(name = "da_value")
    private Long daValue;

    @Column(name = "total_salary")
    private Long totalSalary;

    @Column(name = "city_category")
    private String cityCategory;

    @Column(name = "hra_percent")
    private Long hraPercent;

    @Column(name = "hra_value")
    private Long hraValue;

    @Column(name = "travel_allowance")
    private Long travelAllowance;

    @Column(name = "cla")
    private Long cla;

    @Column(name = "medical")
    private Long medical;

    @Column(name = "gross_salary")
    private Long grossSalary;

    @Column(name = "prof_tax")
    private Long profTax;

    @Column(name = "insurance")
    private Long insurance;

    @Column(name = "gpf")
    private Long gpf;

    @Column(name = "cpg")
    private Long cpg;

    @Column(name = "incometax")
    private Long incometax;

    @Column(name = "total_deduction")
    private Long totalDeduction;

    @Column(name = "net_salary")
    private Long netSalary;

    @ManyToOne
    @JsonIgnoreProperties("monthlySalaryHistories")
    private Faculty faculty;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public MonthlySalaryHistory year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public MonthlySalaryHistory month(Integer month) {
        this.month = month;
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getOfficeName() {
        return officeName;
    }

    public MonthlySalaryHistory officeName(String officeName) {
        this.officeName = officeName;
        return this;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Long getBasicPay() {
        return basicPay;
    }

    public MonthlySalaryHistory basicPay(Long basicPay) {
        this.basicPay = basicPay;
        return this;
    }

    public void setBasicPay(Long basicPay) {
        this.basicPay = basicPay;
    }

    public Long getGradePay() {
        return gradePay;
    }

    public MonthlySalaryHistory gradePay(Long gradePay) {
        this.gradePay = gradePay;
        return this;
    }

    public void setGradePay(Long gradePay) {
        this.gradePay = gradePay;
    }

    public Long getBasicTotal() {
        return basicTotal;
    }

    public MonthlySalaryHistory basicTotal(Long basicTotal) {
        this.basicTotal = basicTotal;
        return this;
    }

    public void setBasicTotal(Long basicTotal) {
        this.basicTotal = basicTotal;
    }

    public Long getDaPercent() {
        return daPercent;
    }

    public MonthlySalaryHistory daPercent(Long daPercent) {
        this.daPercent = daPercent;
        return this;
    }

    public void setDaPercent(Long daPercent) {
        this.daPercent = daPercent;
    }

    public Long getDaValue() {
        return daValue;
    }

    public MonthlySalaryHistory daValue(Long daValue) {
        this.daValue = daValue;
        return this;
    }

    public void setDaValue(Long daValue) {
        this.daValue = daValue;
    }

    public Long getTotalSalary() {
        return totalSalary;
    }

    public MonthlySalaryHistory totalSalary(Long totalSalary) {
        this.totalSalary = totalSalary;
        return this;
    }

    public void setTotalSalary(Long totalSalary) {
        this.totalSalary = totalSalary;
    }

    public String getCityCategory() {
        return cityCategory;
    }

    public MonthlySalaryHistory cityCategory(String cityCategory) {
        this.cityCategory = cityCategory;
        return this;
    }

    public void setCityCategory(String cityCategory) {
        this.cityCategory = cityCategory;
    }

    public Long getHraPercent() {
        return hraPercent;
    }

    public MonthlySalaryHistory hraPercent(Long hraPercent) {
        this.hraPercent = hraPercent;
        return this;
    }

    public void setHraPercent(Long hraPercent) {
        this.hraPercent = hraPercent;
    }

    public Long getHraValue() {
        return hraValue;
    }

    public MonthlySalaryHistory hraValue(Long hraValue) {
        this.hraValue = hraValue;
        return this;
    }

    public void setHraValue(Long hraValue) {
        this.hraValue = hraValue;
    }

    public Long getTravelAllowance() {
        return travelAllowance;
    }

    public MonthlySalaryHistory travelAllowance(Long travelAllowance) {
        this.travelAllowance = travelAllowance;
        return this;
    }

    public void setTravelAllowance(Long travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    public Long getCla() {
        return cla;
    }

    public MonthlySalaryHistory cla(Long cla) {
        this.cla = cla;
        return this;
    }

    public void setCla(Long cla) {
        this.cla = cla;
    }

    public Long getMedical() {
        return medical;
    }

    public MonthlySalaryHistory medical(Long medical) {
        this.medical = medical;
        return this;
    }

    public void setMedical(Long medical) {
        this.medical = medical;
    }

    public Long getGrossSalary() {
        return grossSalary;
    }

    public MonthlySalaryHistory grossSalary(Long grossSalary) {
        this.grossSalary = grossSalary;
        return this;
    }

    public void setGrossSalary(Long grossSalary) {
        this.grossSalary = grossSalary;
    }

    public Long getProfTax() {
        return profTax;
    }

    public MonthlySalaryHistory profTax(Long profTax) {
        this.profTax = profTax;
        return this;
    }

    public void setProfTax(Long profTax) {
        this.profTax = profTax;
    }

    public Long getInsurance() {
        return insurance;
    }

    public MonthlySalaryHistory insurance(Long insurance) {
        this.insurance = insurance;
        return this;
    }

    public void setInsurance(Long insurance) {
        this.insurance = insurance;
    }

    public Long getGpf() {
        return gpf;
    }

    public MonthlySalaryHistory gpf(Long gpf) {
        this.gpf = gpf;
        return this;
    }

    public void setGpf(Long gpf) {
        this.gpf = gpf;
    }

    public Long getCpg() {
        return cpg;
    }

    public MonthlySalaryHistory cpg(Long cpg) {
        this.cpg = cpg;
        return this;
    }

    public void setCpg(Long cpg) {
        this.cpg = cpg;
    }

    public Long getIncometax() {
        return incometax;
    }

    public MonthlySalaryHistory incometax(Long incometax) {
        this.incometax = incometax;
        return this;
    }

    public void setIncometax(Long incometax) {
        this.incometax = incometax;
    }

    public Long getTotalDeduction() {
        return totalDeduction;
    }

    public MonthlySalaryHistory totalDeduction(Long totalDeduction) {
        this.totalDeduction = totalDeduction;
        return this;
    }

    public void setTotalDeduction(Long totalDeduction) {
        this.totalDeduction = totalDeduction;
    }

    public Long getNetSalary() {
        return netSalary;
    }

    public MonthlySalaryHistory netSalary(Long netSalary) {
        this.netSalary = netSalary;
        return this;
    }

    public void setNetSalary(Long netSalary) {
        this.netSalary = netSalary;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public MonthlySalaryHistory faculty(Faculty faculty) {
        this.faculty = faculty;
        return this;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
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
        MonthlySalaryHistory monthlySalaryHistory = (MonthlySalaryHistory) o;
        if (monthlySalaryHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monthlySalaryHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MonthlySalaryHistory{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", month=" + getMonth() +
            ", officeName='" + getOfficeName() + "'" +
            ", basicPay=" + getBasicPay() +
            ", gradePay=" + getGradePay() +
            ", basicTotal=" + getBasicTotal() +
            ", daPercent=" + getDaPercent() +
            ", daValue=" + getDaValue() +
            ", totalSalary=" + getTotalSalary() +
            ", cityCategory='" + getCityCategory() + "'" +
            ", hraPercent=" + getHraPercent() +
            ", hraValue=" + getHraValue() +
            ", travelAllowance=" + getTravelAllowance() +
            ", cla=" + getCla() +
            ", medical=" + getMedical() +
            ", grossSalary=" + getGrossSalary() +
            ", profTax=" + getProfTax() +
            ", insurance=" + getInsurance() +
            ", gpf=" + getGpf() +
            ", cpg=" + getCpg() +
            ", incometax=" + getIncometax() +
            ", totalDeduction=" + getTotalDeduction() +
            ", netSalary=" + getNetSalary() +
            "}";
    }
}
