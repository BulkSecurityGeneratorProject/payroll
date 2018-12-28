import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayrollSharedModule } from 'app/shared';
import {
    DAHistoryComponent,
    DAHistoryDetailComponent,
    DAHistoryUpdateComponent,
    DAHistoryDeletePopupComponent,
    DAHistoryDeleteDialogComponent,
    dAHistoryRoute,
    dAHistoryPopupRoute
} from './';

const ENTITY_STATES = [...dAHistoryRoute, ...dAHistoryPopupRoute];

@NgModule({
    imports: [PayrollSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DAHistoryComponent,
        DAHistoryDetailComponent,
        DAHistoryUpdateComponent,
        DAHistoryDeleteDialogComponent,
        DAHistoryDeletePopupComponent
    ],
    entryComponents: [DAHistoryComponent, DAHistoryUpdateComponent, DAHistoryDeleteDialogComponent, DAHistoryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayrollDAHistoryModule {}
