import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MedicalHistory } from 'app/shared/model/medical-history.model';
import { MedicalHistoryService } from './medical-history.service';
import { MedicalHistoryComponent } from './medical-history.component';
import { MedicalHistoryDetailComponent } from './medical-history-detail.component';
import { MedicalHistoryUpdateComponent } from './medical-history-update.component';
import { MedicalHistoryDeletePopupComponent } from './medical-history-delete-dialog.component';
import { IMedicalHistory } from 'app/shared/model/medical-history.model';

@Injectable({ providedIn: 'root' })
export class MedicalHistoryResolve implements Resolve<IMedicalHistory> {
    constructor(private service: MedicalHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MedicalHistory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MedicalHistory>) => response.ok),
                map((medicalHistory: HttpResponse<MedicalHistory>) => medicalHistory.body)
            );
        }
        return of(new MedicalHistory());
    }
}

export const medicalHistoryRoute: Routes = [
    {
        path: 'medical-history',
        component: MedicalHistoryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'MedicalHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'medical-history/:id/view',
        component: MedicalHistoryDetailComponent,
        resolve: {
            medicalHistory: MedicalHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'MedicalHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'medical-history/new',
        component: MedicalHistoryUpdateComponent,
        resolve: {
            medicalHistory: MedicalHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'MedicalHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'medical-history/:id/edit',
        component: MedicalHistoryUpdateComponent,
        resolve: {
            medicalHistory: MedicalHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'MedicalHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const medicalHistoryPopupRoute: Routes = [
    {
        path: 'medical-history/:id/delete',
        component: MedicalHistoryDeletePopupComponent,
        resolve: {
            medicalHistory: MedicalHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'MedicalHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
