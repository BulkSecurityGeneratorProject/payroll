package in.payroll.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MonthlySalaryHistory entity.
 */
public class MonthlySalaryHistoryDTO implements Serializable {

    private Long id;

    private Integer year;

    private Integer month;

    private String officeName;

    private Long basicPay;

    private Long gradePay;

    private Long basicTotal;

    private Long daPercent;

    private Long daValue;

    private Long totalSalary;

    private String cityCategory;

    private Long hraPercent;

    private Long hraValue;

    private Long travelAllowance;

    private Long cla;

    private Long medical;

    private Long grossSalary;

    private Long profTax;

    private Long insurance;

    private Long gpf;

    private Long cpg;

    private Long incometax;

    private Long totalDeduction;

    private Long netSalary;

    private Long facultyId;

    private String facultyFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
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

    public Long getBasicTotal() {
        return basicTotal;
    }

    public void setBasicTotal(Long basicTotal) {
        this.basicTotal = basicTotal;
    }

    public Long getDaPercent() {
        return daPercent;
    }

    public void setDaPercent(Long daPercent) {
        this.daPercent = daPercent;
    }

    public Long getDaValue() {
        return daValue;
    }

    public void setDaValue(Long daValue) {
        this.daValue = daValue;
    }

    public Long getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Long totalSalary) {
        this.totalSalary = totalSalary;
    }

    public String getCityCategory() {
        return cityCategory;
    }

    public void setCityCategory(String cityCategory) {
        this.cityCategory = cityCategory;
    }

    public Long getHraPercent() {
        return hraPercent;
    }

    public void setHraPercent(Long hraPercent) {
        this.hraPercent = hraPercent;
    }

    public Long getHraValue() {
        return hraValue;
    }

    public void setHraValue(Long hraValue) {
        this.hraValue = hraValue;
    }

    public Long getTravelAllowance() {
        return travelAllowance;
    }

    public void setTravelAllowance(Long travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    public Long getCla() {
        return cla;
    }

    public void setCla(Long cla) {
        this.cla = cla;
    }

    public Long getMedical() {
        return medical;
    }

    public void setMedical(Long medical) {
        this.medical = medical;
    }

    public Long getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(Long grossSalary) {
        this.grossSalary = grossSalary;
    }

    public Long getProfTax() {
        return profTax;
    }

    public void setProfTax(Long profTax) {
        this.profTax = profTax;
    }

    public Long getInsurance() {
        return insurance;
    }

    public void setInsurance(Long insurance) {
        this.insurance = insurance;
    }

    public Long getGpf() {
        return gpf;
    }

    public void setGpf(Long gpf) {
        this.gpf = gpf;
    }

    public Long getCpg() {
        return cpg;
    }

    public void setCpg(Long cpg) {
        this.cpg = cpg;
    }

    public Long getIncometax() {
        return incometax;
    }

    public void setIncometax(Long incometax) {
        this.incometax = incometax;
    }

    public Long getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(Long totalDeduction) {
        this.totalDeduction = totalDeduction;
    }

    public Long getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Long netSalary) {
        this.netSalary = netSalary;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyFirstName() {
        return facultyFirstName;
    }

    public void setFacultyFirstName(String facultyFirstName) {
        this.facultyFirstName = facultyFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MonthlySalaryHistoryDTO monthlySalaryHistoryDTO = (MonthlySalaryHistoryDTO) o;
        if (monthlySalaryHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monthlySalaryHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MonthlySalaryHistoryDTO{" +
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
            ", faculty=" + getFacultyId() +
            ", faculty='" + getFacultyFirstName() + "'" +
            "}";
    }
}
