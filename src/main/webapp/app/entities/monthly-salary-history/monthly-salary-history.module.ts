import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayrollSharedModule } from 'app/shared';
import {
    MonthlySalaryHistoryComponent,
    MonthlySalaryHistoryDetailComponent,
    MonthlySalaryHistoryUpdateComponent,
    MonthlySalaryHistoryDeletePopupComponent,
    MonthlySalaryHistoryDeleteDialogComponent,
    monthlySalaryHistoryRoute,
    monthlySalaryHistoryPopupRoute
} from './';

const ENTITY_STATES = [...monthlySalaryHistoryRoute, ...monthlySalaryHistoryPopupRoute];

@NgModule({
    imports: [PayrollSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MonthlySalaryHistoryComponent,
        MonthlySalaryHistoryDetailComponent,
        MonthlySalaryHistoryUpdateComponent,
        MonthlySalaryHistoryDeleteDialogComponent,
        MonthlySalaryHistoryDeletePopupComponent
    ],
    entryComponents: [
        MonthlySalaryHistoryComponent,
        MonthlySalaryHistoryUpdateComponent,
        MonthlySalaryHistoryDeleteDialogComponent,
        MonthlySalaryHistoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayrollMonthlySalaryHistoryModule {}
