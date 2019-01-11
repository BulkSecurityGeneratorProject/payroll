package in.payroll.service;

import in.payroll.service.dto.CurrentSalaryDTO;
import in.payroll.service.dto.MonthlySalaryHistoryDTO;

public interface SalaryCalculationService {

    public void updateMonthlySalary(CurrentSalaryDTO currentSalaryDTO);

    public void updateMonthlySalary(MonthlySalaryHistoryDTO monthlySalaryHistoryDTO);
}
