package in.payroll.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MedicalHistory.
 */
@Entity
@Table(name = "medical_history")
public class MedicalHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "current_value")
    private Long currentValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public MedicalHistory date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public MedicalHistory currentValue(Long currentValue) {
        this.currentValue = currentValue;
        return this;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
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
        MedicalHistory medicalHistory = (MedicalHistory) o;
        if (medicalHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalHistory{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", currentValue=" + getCurrentValue() +
            "}";
    }
}
