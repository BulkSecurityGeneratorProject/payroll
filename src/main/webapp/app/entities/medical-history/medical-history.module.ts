import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayrollSharedModule } from 'app/shared';
import {
    MedicalHistoryComponent,
    MedicalHistoryDetailComponent,
    MedicalHistoryUpdateComponent,
    MedicalHistoryDeletePopupComponent,
    MedicalHistoryDeleteDialogComponent,
    medicalHistoryRoute,
    medicalHistoryPopupRoute
} from './';

const ENTITY_STATES = [...medicalHistoryRoute, ...medicalHistoryPopupRoute];

@NgModule({
    imports: [PayrollSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MedicalHistoryComponent,
        MedicalHistoryDetailComponent,
        MedicalHistoryUpdateComponent,
        MedicalHistoryDeleteDialogComponent,
        MedicalHistoryDeletePopupComponent
    ],
    entryComponents: [
        MedicalHistoryComponent,
        MedicalHistoryUpdateComponent,
        MedicalHistoryDeleteDialogComponent,
        MedicalHistoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayrollMedicalHistoryModule {}
