package in.payroll.service;

import in.payroll.domain.CurrentSalary;
import in.payroll.domain.Faculty;
import in.payroll.domain.Salary;

import java.util.Map;

public interface SalaryEstimatorService {
    Map<Integer, Map<Integer, Salary>> calculatePension(Faculty faculty, CurrentSalary currentSalary);
}
