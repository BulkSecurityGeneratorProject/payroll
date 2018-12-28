package in.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Faculty.
 */
@Entity
@Table(name = "faculty")
public class Faculty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "designation")
    private String designation;

    @OneToOne    @JoinColumn(unique = true)
    private User user;

    @OneToOne    @JoinColumn(unique = true)
    private CurrentSalary currentSalary;

    @OneToMany(mappedBy = "faculty")
    private Set<MonthlySalaryHistory> monthlySalaryHistories = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Faculty firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Faculty lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public Faculty designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public User getUser() {
        return user;
    }

    public Faculty user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CurrentSalary getCurrentSalary() {
        return currentSalary;
    }

    public Faculty currentSalary(CurrentSalary currentSalary) {
        this.currentSalary = currentSalary;
        return this;
    }

    public void setCurrentSalary(CurrentSalary currentSalary) {
        this.currentSalary = currentSalary;
    }

    public Set<MonthlySalaryHistory> getMonthlySalaryHistories() {
        return monthlySalaryHistories;
    }

    public Faculty monthlySalaryHistories(Set<MonthlySalaryHistory> monthlySalaryHistories) {
        this.monthlySalaryHistories = monthlySalaryHistories;
        return this;
    }

    public Faculty addMonthlySalaryHistory(MonthlySalaryHistory monthlySalaryHistory) {
        this.monthlySalaryHistories.add(monthlySalaryHistory);
        monthlySalaryHistory.setFaculty(this);
        return this;
    }

    public Faculty removeMonthlySalaryHistory(MonthlySalaryHistory monthlySalaryHistory) {
        this.monthlySalaryHistories.remove(monthlySalaryHistory);
        monthlySalaryHistory.setFaculty(null);
        return this;
    }

    public void setMonthlySalaryHistories(Set<MonthlySalaryHistory> monthlySalaryHistories) {
        this.monthlySalaryHistories = monthlySalaryHistories;
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
        Faculty faculty = (Faculty) o;
        if (faculty.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), faculty.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Faculty{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
