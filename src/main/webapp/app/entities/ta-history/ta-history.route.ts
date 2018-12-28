import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TAHistory } from 'app/shared/model/ta-history.model';
import { TAHistoryService } from './ta-history.service';
import { TAHistoryComponent } from './ta-history.component';
import { TAHistoryDetailComponent } from './ta-history-detail.component';
import { TAHistoryUpdateComponent } from './ta-history-update.component';
import { TAHistoryDeletePopupComponent } from './ta-history-delete-dialog.component';
import { ITAHistory } from 'app/shared/model/ta-history.model';

@Injectable({ providedIn: 'root' })
export class TAHistoryResolve implements Resolve<ITAHistory> {
    constructor(private service: TAHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TAHistory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TAHistory>) => response.ok),
                map((tAHistory: HttpResponse<TAHistory>) => tAHistory.body)
            );
        }
        return of(new TAHistory());
    }
}

export const tAHistoryRoute: Routes = [
    {
        path: 'ta-history',
        component: TAHistoryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'TAHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ta-history/:id/view',
        component: TAHistoryDetailComponent,
        resolve: {
            tAHistory: TAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'TAHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ta-history/new',
        component: TAHistoryUpdateComponent,
        resolve: {
            tAHistory: TAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'TAHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ta-history/:id/edit',
        component: TAHistoryUpdateComponent,
        resolve: {
            tAHistory: TAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'TAHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tAHistoryPopupRoute: Routes = [
    {
        path: 'ta-history/:id/delete',
        component: TAHistoryDeletePopupComponent,
        resolve: {
            tAHistory: TAHistoryResolve
        },
        data: {
            authorities: ['FINANCE_ADMIN'],
            pageTitle: 'TAHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
