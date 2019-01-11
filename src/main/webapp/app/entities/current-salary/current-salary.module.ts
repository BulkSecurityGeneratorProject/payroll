import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayrollSharedModule } from 'app/shared';
import {
    CurrentSalaryComponent,
    CurrentSalaryDetailComponent,
    CurrentSalaryUpdateComponent,
    CurrentSalaryDeletePopupComponent,
    CurrentSalaryDeleteDialogComponent,
    currentSalaryRoute,
    currentSalaryPopupRoute
} from './';

const ENTITY_STATES = [...currentSalaryRoute, ...currentSalaryPopupRoute];

@NgModule({
    imports: [PayrollSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CurrentSalaryComponent,
        CurrentSalaryDetailComponent,
        CurrentSalaryUpdateComponent,
        CurrentSalaryDeleteDialogComponent,
        CurrentSalaryDeletePopupComponent
    ],
    entryComponents: [
        CurrentSalaryComponent,
        CurrentSalaryUpdateComponent,
        CurrentSalaryDeleteDialogComponent,
        CurrentSalaryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayrollCurrentSalaryModule {}
