import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayrollSharedModule } from 'app/shared';
import {
    HRAHistoryComponent,
    HRAHistoryDetailComponent,
    HRAHistoryUpdateComponent,
    HRAHistoryDeletePopupComponent,
    HRAHistoryDeleteDialogComponent,
    hRAHistoryRoute,
    hRAHistoryPopupRoute
} from './';

const ENTITY_STATES = [...hRAHistoryRoute, ...hRAHistoryPopupRoute];

@NgModule({
    imports: [PayrollSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HRAHistoryComponent,
        HRAHistoryDetailComponent,
        HRAHistoryUpdateComponent,
        HRAHistoryDeleteDialogComponent,
        HRAHistoryDeletePopupComponent
    ],
    entryComponents: [HRAHistoryComponent, HRAHistoryUpdateComponent, HRAHistoryDeleteDialogComponent, HRAHistoryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayrollHRAHistoryModule {}
