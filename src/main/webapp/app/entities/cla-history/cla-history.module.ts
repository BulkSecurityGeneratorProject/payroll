import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayrollSharedModule } from 'app/shared';
import {
    CLAHistoryComponent,
    CLAHistoryDetailComponent,
    CLAHistoryUpdateComponent,
    CLAHistoryDeletePopupComponent,
    CLAHistoryDeleteDialogComponent,
    cLAHistoryRoute,
    cLAHistoryPopupRoute
} from './';

const ENTITY_STATES = [...cLAHistoryRoute, ...cLAHistoryPopupRoute];

@NgModule({
    imports: [PayrollSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CLAHistoryComponent,
        CLAHistoryDetailComponent,
        CLAHistoryUpdateComponent,
        CLAHistoryDeleteDialogComponent,
        CLAHistoryDeletePopupComponent
    ],
    entryComponents: [CLAHistoryComponent, CLAHistoryUpdateComponent, CLAHistoryDeleteDialogComponent, CLAHistoryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayrollCLAHistoryModule {}
