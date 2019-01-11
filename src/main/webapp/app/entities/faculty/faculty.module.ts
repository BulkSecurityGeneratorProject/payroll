import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayrollSharedModule } from 'app/shared';
import { PayrollAdminModule } from 'app/admin/admin.module';
import {
    FacultyComponent,
    FacultyDetailComponent,
    FacultyUpdateComponent,
    FacultyDeletePopupComponent,
    FacultyDeleteDialogComponent,
    facultyRoute,
    facultyPopupRoute
} from './';

const ENTITY_STATES = [...facultyRoute, ...facultyPopupRoute];

@NgModule({
    imports: [PayrollSharedModule, PayrollAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FacultyComponent,
        FacultyDetailComponent,
        FacultyUpdateComponent,
        FacultyDeleteDialogComponent,
        FacultyDeletePopupComponent
    ],
    entryComponents: [FacultyComponent, FacultyUpdateComponent, FacultyDeleteDialogComponent, FacultyDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayrollFacultyModule {}
