import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HRAHistory } from 'app/shared/model/hra-history.model';
import { HRAHistoryService } from './hra-history.service';
import { HRAHistoryComponent } from './hra-history.component';
import { HRAHistoryDetailComponent } from './hra-history-detail.component';
import { HRAHistoryUpdateComponent } from './hra-history-update.component';
import { HRAHistoryDeletePopupComponent } from './hra-history-delete-dialog.component';
import { IHRAHistory } from 'app/shared/model/hra-history.model';

@Injectable({ providedIn: 'root' })
export class HRAHistoryResolve implements Resolve<IHRAHistory> {
    constructor(private service: HRAHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HRAHistory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HRAHistory>) => response.ok),
                map((hRAHistory: HttpResponse<HRAHistory>) => hRAHistory.body)
            );
        }
        return of(new HRAHistory());
    }
}

export const hRAHistoryRoute: Routes = [
    {
        path: 'hra-history',
        component: HRAHistoryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'HRAHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hra-history/:id/view',
        component: HRAHistoryDetailComponent,
        resolve: {
            hRAHistory: HRAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'HRAHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hra-history/new',
        component: HRAHistoryUpdateComponent,
        resolve: {
            hRAHistory: HRAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'HRAHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hra-history/:id/edit',
        component: HRAHistoryUpdateComponent,
        resolve: {
            hRAHistory: HRAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'HRAHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hRAHistoryPopupRoute: Routes = [
    {
        path: 'hra-history/:id/delete',
        component: HRAHistoryDeletePopupComponent,
        resolve: {
            hRAHistory: HRAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'HRAHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
