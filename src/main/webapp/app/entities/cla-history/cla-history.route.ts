import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CLAHistory } from 'app/shared/model/cla-history.model';
import { CLAHistoryService } from './cla-history.service';
import { CLAHistoryComponent } from './cla-history.component';
import { CLAHistoryDetailComponent } from './cla-history-detail.component';
import { CLAHistoryUpdateComponent } from './cla-history-update.component';
import { CLAHistoryDeletePopupComponent } from './cla-history-delete-dialog.component';
import { ICLAHistory } from 'app/shared/model/cla-history.model';

@Injectable({ providedIn: 'root' })
export class CLAHistoryResolve implements Resolve<ICLAHistory> {
    constructor(private service: CLAHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CLAHistory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CLAHistory>) => response.ok),
                map((cLAHistory: HttpResponse<CLAHistory>) => cLAHistory.body)
            );
        }
        return of(new CLAHistory());
    }
}

export const cLAHistoryRoute: Routes = [
    {
        path: 'cla-history',
        component: CLAHistoryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'CLAHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cla-history/:id/view',
        component: CLAHistoryDetailComponent,
        resolve: {
            cLAHistory: CLAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'CLAHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cla-history/new',
        component: CLAHistoryUpdateComponent,
        resolve: {
            cLAHistory: CLAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'CLAHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cla-history/:id/edit',
        component: CLAHistoryUpdateComponent,
        resolve: {
            cLAHistory: CLAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'CLAHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cLAHistoryPopupRoute: Routes = [
    {
        path: 'cla-history/:id/delete',
        component: CLAHistoryDeletePopupComponent,
        resolve: {
            cLAHistory: CLAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'CLAHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
