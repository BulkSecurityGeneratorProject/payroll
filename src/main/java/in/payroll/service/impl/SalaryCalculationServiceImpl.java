package in.payroll.service.impl;

import in.payroll.service.*;
import in.payroll.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class SalaryCalculationServiceImpl implements SalaryCalculationService {

    private final Logger log = LoggerFactory.getLogger(SalaryCalculationServiceImpl.class);

    @Autowired
    private MonthlySalaryHistoryService monthlySalaryHistoryService;

    @Autowired
    private TAHistoryService tAHistoryService;

    @Autowired
    private CLAHistoryService claHistoryService;

    @Autowired
    private DAHistoryService daHistoryService;

    @Autowired
    private HRAHistoryService hraHistoryService;

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @Override
    public void updateMonthlySalary(CurrentSalaryDTO currentSalaryDTO){
        LocalDate ld = LocalDate.now();
        Optional<MonthlySalaryHistoryDTO> monthlySalary =
            monthlySalaryHistoryService.findOneByYearAndByMonth(ld.getYear(),ld.getMonthValue());

        MonthlySalaryHistoryDTO monthlySalaryHistoryDTO;
        if(monthlySalary.isPresent())
            monthlySalaryHistoryDTO = monthlySalary.get();
        else
            monthlySalaryHistoryDTO = new MonthlySalaryHistoryDTO();

        TAHistoryDTO ta = tAHistoryService.findOneByCityCategory(currentSalaryDTO.getCityCategory()).get();
        HRAHistoryDTO hra = hraHistoryService.findOneByCityCategory(currentSalaryDTO.getCityCategory()).get();
        CLAHistoryDTO cla = claHistoryService.findOneByCityCategory(currentSalaryDTO.getCityCategory()).get();
        DAHistoryDTO da = daHistoryService.findOneByDate().get();
        MedicalHistoryDTO medical = medicalHistoryService.findOneByDate().get();

        monthlySalaryHistoryDTO.setYear(ld.getYear());
        monthlySalaryHistoryDTO.setMonth(ld.getMonthValue());
        monthlySalaryHistoryDTO.setBasicPay(currentSalaryDTO.getBasicPay());
        monthlySalaryHistoryDTO.setGradePay(currentSalaryDTO.getGradePay());
        monthlySalaryHistoryDTO.setOfficeName(currentSalaryDTO.getOfficeOfficeName());
        monthlySalaryHistoryDTO.setDaPercent(da.getCurrentValue());
        monthlySalaryHistoryDTO.setCityCategory(currentSalaryDTO.getCityCategory());
        monthlySalaryHistoryDTO.setHraPercent(hra.getCurrentValue());
        monthlySalaryHistoryDTO.setCla(cla.getCurrentValue());
        monthlySalaryHistoryDTO.setMedical(medical.getCurrentValue());
        monthlySalaryHistoryDTO.setTravelAllowance(ta.getCurrentValue());

        calculateMonthlySalary(monthlySalaryHistoryDTO);
        log.info(monthlySalaryHistoryDTO.toString());
        monthlySalaryHistoryService.save(monthlySalaryHistoryDTO);

    }

    @Override
    public void updateMonthlySalary(MonthlySalaryHistoryDTO monthlySalaryHistoryDTO){
        calculateMonthlySalary(monthlySalaryHistoryDTO);
        log.info(monthlySalaryHistoryDTO.toString());
        monthlySalaryHistoryService.save(monthlySalaryHistoryDTO);
    }

    private void calculateMonthlySalary(MonthlySalaryHistoryDTO monthlySalaryHistoryDTO) {


        monthlySalaryHistoryDTO.setBasicTotal(monthlySalaryHistoryDTO.getBasicPay() + monthlySalaryHistoryDTO.getGradePay());
        monthlySalaryHistoryDTO.setDaValue(( monthlySalaryHistoryDTO.getBasicTotal() *
            monthlySalaryHistoryDTO.getDaPercent() ) / 100);
        monthlySalaryHistoryDTO.setTotalSalary(monthlySalaryHistoryDTO.getBasicTotal() + monthlySalaryHistoryDTO.getDaValue());

        monthlySalaryHistoryDTO.setHraValue(( monthlySalaryHistoryDTO.getBasicTotal()
            * monthlySalaryHistoryDTO.getHraPercent() ) / 100);

        monthlySalaryHistoryDTO.setGrossSalary(monthlySalaryHistoryDTO.getTotalSalary()
            + monthlySalaryHistoryDTO.getHraValue()
            + monthlySalaryHistoryDTO.getTravelAllowance()
            + monthlySalaryHistoryDTO.getCla()
            + monthlySalaryHistoryDTO.getMedical() );
        monthlySalaryHistoryDTO.setGpf(0L);
        monthlySalaryHistoryDTO.setCpg(( monthlySalaryHistoryDTO.getTotalSalary() * 10 ) / 100 ) ;
        monthlySalaryHistoryDTO.setProfTax(200L);
        monthlySalaryHistoryDTO.setInsurance(300L);
        monthlySalaryHistoryDTO.setIncometax(1000L);
        monthlySalaryHistoryDTO.setTotalDeduction( monthlySalaryHistoryDTO.getCpg()
            + monthlySalaryHistoryDTO.getGpf()
            + monthlySalaryHistoryDTO.getProfTax()
            + monthlySalaryHistoryDTO.getInsurance()
            + monthlySalaryHistoryDTO.getIncometax());
        monthlySalaryHistoryDTO.setNetSalary(monthlySalaryHistoryDTO.getGrossSalary() - monthlySalaryHistoryDTO.getTotalDeduction());


        return;
    }


}
