import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PayrollFacultyModule } from './faculty/faculty.module';
import { PayrollMonthlySalaryHistoryModule } from './monthly-salary-history/monthly-salary-history.module';
import { PayrollOfficeModule } from './office/office.module';
import { PayrollCurrentSalaryModule } from './current-salary/current-salary.module';
import { PayrollDAHistoryModule } from './da-history/da-history.module';
import { PayrollHRAHistoryModule } from './hra-history/hra-history.module';
import { PayrollTAHistoryModule } from './ta-history/ta-history.module';
import { PayrollMedicalHistoryModule } from './medical-history/medical-history.module';
import { PayrollCLAHistoryModule } from './cla-history/cla-history.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PayrollFacultyModule,
        PayrollMonthlySalaryHistoryModule,
        PayrollOfficeModule,
        PayrollCurrentSalaryModule,
        PayrollDAHistoryModule,
        PayrollHRAHistoryModule,
        PayrollTAHistoryModule,
        PayrollMedicalHistoryModule,
        PayrollCLAHistoryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayrollEntityModule {}
