import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayrollSharedModule } from 'app/shared';
import {
    TAHistoryComponent,
    TAHistoryDetailComponent,
    TAHistoryUpdateComponent,
    TAHistoryDeletePopupComponent,
    TAHistoryDeleteDialogComponent,
    tAHistoryRoute,
    tAHistoryPopupRoute
} from './';

const ENTITY_STATES = [...tAHistoryRoute, ...tAHistoryPopupRoute];

@NgModule({
    imports: [PayrollSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TAHistoryComponent,
        TAHistoryDetailComponent,
        TAHistoryUpdateComponent,
        TAHistoryDeleteDialogComponent,
        TAHistoryDeletePopupComponent
    ],
    entryComponents: [TAHistoryComponent, TAHistoryUpdateComponent, TAHistoryDeleteDialogComponent, TAHistoryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayrollTAHistoryModule {}
