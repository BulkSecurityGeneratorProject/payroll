import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MonthlySalaryHistory } from 'app/shared/model/monthly-salary-history.model';
import { MonthlySalaryHistoryService } from './monthly-salary-history.service';
import { MonthlySalaryHistoryComponent } from './monthly-salary-history.component';
import { MonthlySalaryHistoryDetailComponent } from './monthly-salary-history-detail.component';
import { MonthlySalaryHistoryUpdateComponent } from './monthly-salary-history-update.component';
import { MonthlySalaryHistoryDeletePopupComponent } from './monthly-salary-history-delete-dialog.component';
import { IMonthlySalaryHistory } from 'app/shared/model/monthly-salary-history.model';

@Injectable({ providedIn: 'root' })
export class MonthlySalaryHistoryResolve implements Resolve<IMonthlySalaryHistory> {
    constructor(private service: MonthlySalaryHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MonthlySalaryHistory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MonthlySalaryHistory>) => response.ok),
                map((monthlySalaryHistory: HttpResponse<MonthlySalaryHistory>) => monthlySalaryHistory.body)
            );
        }
        return of(new MonthlySalaryHistory());
    }
}

export const monthlySalaryHistoryRoute: Routes = [
    {
        path: 'monthly-salary-history',
        component: MonthlySalaryHistoryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'MonthlySalaryHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'monthly-salary-history/:id/view',
        component: MonthlySalaryHistoryDetailComponent,
        resolve: {
            monthlySalaryHistory: MonthlySalaryHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlySalaryHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'monthly-salary-history/new',
        component: MonthlySalaryHistoryUpdateComponent,
        resolve: {
            monthlySalaryHistory: MonthlySalaryHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlySalaryHistories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'monthly-salary-history/:id/edit',
        component: MonthlySalaryHistoryUpdateComponent,
        resolve: {
            monthlySalaryHistory: MonthlySalaryHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlySalaryHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const monthlySalaryHistoryPopupRoute: Routes = [
    {
        path: 'monthly-salary-history/:id/delete',
        component: MonthlySalaryHistoryDeletePopupComponent,
        resolve: {
            monthlySalaryHistory: MonthlySalaryHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlySalaryHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
