package in.payroll.service.impl;

import in.payroll.domain.CurrentSalary;
import in.payroll.domain.Faculty;
import in.payroll.domain.Salary;
import in.payroll.service.SalaryEstimatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
public class SalaryEstimatorServiceImpl implements SalaryEstimatorService {

    private final Logger log = LoggerFactory.getLogger(SalaryEstimatorServiceImpl.class);

    @Override
    public Map<Integer, Map<Integer, Salary>> calculatePension(Faculty faculty, CurrentSalary currentSalary) {
        Map<Integer, Map<Integer, Salary>> yearlySalaryMap = new LinkedHashMap<>();


        Integer cpfInterestRate = 9;
        Integer annualBasicIncrease = 3;
        Integer halfYearlyDaIncrease = 4;

        Integer basicStart = currentSalary.getBasicPay().intValue();
        Integer gpStart = currentSalary.getGradePay().intValue();
        Integer daPercentStart = 132;
        Integer startYear = 2016;
        Integer startMonth = 12;

        Integer year = startYear;
        Integer month = startMonth;

        Integer basic = basicStart;
        Integer gp = gpStart;
        Integer daPercent = daPercentStart;
        LocalDate dt = LocalDate.now();
        Map<Integer, Salary> monthlySalary;
        Integer totalCpf = 0;
        Integer totalCpfInterest = 0;
        Integer yearlyCpf = 0;
        Integer yearlyCpfInterest = 0;
        Integer totalYearlyCpf = 0;
        Integer yearsOfService = -1;


        while (year <= 2048) {
            if (year == 2048 && month == 6) {
                break;
            }
            Salary s = new Salary();
            s.setYear(year);
            s.setMonth(month);
            s.setBasic(basic);
            s.setGp(gp);
            s.setTotalBasic(basic + gp);
            s.setDaPercent(daPercent);
            s.setDa((s.getTotalBasic() * daPercent) / 100);
            s.setTotalSalary(s.getTotalBasic() + s.getDa());
            int cpf = (int) Math.round(s.getTotalSalary() * 0.10);
            s.setCpf(cpf);
            s.setTotalCpf(s.getCpf() * 2);
            dt = dt.withYear(year);
            dt = dt.withMonth(month);
            s.setDate(dt);
            totalCpf += s.getTotalCpf();
            yearlyCpf += s.getTotalCpf();
            log.info(s.toString());
            if (yearlySalaryMap.containsKey(year)) {
                monthlySalary = yearlySalaryMap.get(year);
            } else {
                monthlySalary = new LinkedHashMap<>();
                yearlySalaryMap.put(year, monthlySalary);
            }
            monthlySalary.put(month, s);

            if (month == 7) {
                basic = (int) basic + (basic * annualBasicIncrease) / 100;
                log.info(year + " july basic increase =" + basic);
                daPercent = daPercent + halfYearlyDaIncrease;
                log.info(year + " july da increase =" + daPercent);
            }
            // 7000 to 8000 5 year
            // 8000 to 9000 3 year
            if ((month - startMonth) == 0) {
                yearsOfService++;

                if (gpStart == 5400) {
                    if ((year - startYear) == 6) {
                        gp = 6000;
                    }
                    if ((year - startYear) == 9) {
                        gp = 7000;
                    }
                    if ((year - startYear) == 14) {
                        basic = 37400;
                        gp = 8000;
                    }
                    if ((year - startYear) == 17) {
                        gp = 9000;
                    }

                }
                log.info(year + " , years of service := " + yearsOfService);
                log.info("basic " + basic + " gp " + gp);
            }

            month++;
            if (month == 13) {

                yearlyCpfInterest = totalCpf * cpfInterestRate / 100;
                totalCpfInterest += yearlyCpfInterest;
                log.info(year + " Year cpf =" + yearlyCpf);
                log.info(year + " Year cpf interest =" + yearlyCpfInterest);
                log.info(year + " total cpf =" + totalCpf);
                log.info(year + " total cpf interest =" + totalCpfInterest);
                yearlyCpfInterest = 0;
                yearlyCpf = 0;

                year++;
                month = 1;
                daPercent = daPercent + halfYearlyDaIncrease;
                log.info(year + " January da increase =" + daPercent);
            }

        }

        log.info(year + " total cpf =" + totalCpf);
        log.info(year + " total cpf interest =" + totalCpfInterest);
        log.info(year + " total cpf value =" + (totalCpf + totalCpfInterest));
        return yearlySalaryMap;
    }

}
