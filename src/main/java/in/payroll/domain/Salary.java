package in.payroll.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Salary {

    private Integer year;
    private Integer month;
    private LocalDate date;
    private Integer basic;
    private Integer gp;
    private Integer totalBasic;
    private Integer daPercent;
    private Integer da;
    private Integer totalSalary;
    private Integer cpf;
    private Integer totalCpf;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getBasic() {
        return basic;
    }

    public void setBasic(Integer basic) {
        this.basic = basic;
    }

    public Integer getGp() {
        return gp;
    }

    public void setGp(Integer gp) {
        this.gp = gp;
    }

    public Integer getTotalBasic() {
        return totalBasic;
    }

    public void setTotalBasic(Integer totalBasic) {
        this.totalBasic = totalBasic;
    }

    public Integer getDaPercent() {
        return daPercent;
    }

    public void setDaPercent(Integer daPercent) {
        this.daPercent = daPercent;
    }

    public Integer getDa() {
        return da;
    }

    public void setDa(Integer da) {
        this.da = da;
    }

    public Integer getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Integer totalSalary) {
        this.totalSalary = totalSalary;
    }

    public Integer getCpf() {
        return cpf;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public Integer getTotalCpf() {
        return totalCpf;
    }

    public void setTotalCpf(Integer totalCpf) {
        this.totalCpf = totalCpf;
    }

    @Override
    public String toString() {
        return "Salary{" + "year=" + year + ", month=" + month + ", date=" + date + ", basic=" + basic + ", gp=" + gp + ", totalBasic=" + totalBasic + ", daPercent=" + daPercent + ", da=" + da + ", totalSalary=" + totalSalary + ", cpf=" + cpf + ", totalCpf=" + totalCpf + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.year);
        hash = 71 * hash + Objects.hashCode(this.month);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Salary other = (Salary) obj;
        if (!Objects.equals(this.year, other.year)) {
            return false;
        }
        if (!Objects.equals(this.month, other.month)) {
            return false;
        }
        return true;
    }

}
