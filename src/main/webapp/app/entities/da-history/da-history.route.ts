import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DAHistory } from 'app/shared/model/da-history.model';
import { DAHistoryService } from './da-history.service';
import { DAHistoryComponent } from './da-history.component';
import { DAHistoryDetailComponent } from './da-history-detail.component';
import { DAHistoryUpdateComponent } from './da-history-update.component';
import { DAHistoryDeletePopupComponent } from './da-history-delete-dialog.component';
import { IDAHistory } from 'app/shared/model/da-history.model';

@Injectable({ providedIn: 'root' })
export class DAHistoryResolve implements Resolve<IDAHistory> {
    constructor(private service: DAHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DAHistory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DAHistory>) => response.ok),
                map((dAHistory: HttpResponse<DAHistory>) => dAHistory.body)
            );
        }
        return of(new DAHistory());
    }
}

export const dAHistoryRoute: Routes = [
    {
        path: 'da-history',
        component: DAHistoryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'DAHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'da-history/:id/view',
        component: DAHistoryDetailComponent,
        resolve: {
            dAHistory: DAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'DAHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'da-history/new',
        component: DAHistoryUpdateComponent,
        resolve: {
            dAHistory: DAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'DAHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'da-history/:id/edit',
        component: DAHistoryUpdateComponent,
        resolve: {
            dAHistory: DAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'DAHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dAHistoryPopupRoute: Routes = [
    {
        path: 'da-history/:id/delete',
        component: DAHistoryDeletePopupComponent,
        resolve: {
            dAHistory: DAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'DAHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
