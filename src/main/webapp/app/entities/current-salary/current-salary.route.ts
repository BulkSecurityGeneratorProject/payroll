import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CurrentSalary } from 'app/shared/model/current-salary.model';
import { CurrentSalaryService } from './current-salary.service';
import { CurrentSalaryComponent } from './current-salary.component';
import { CurrentSalaryDetailComponent } from './current-salary-detail.component';
import { CurrentSalaryUpdateComponent } from './current-salary-update.component';
import { CurrentSalaryDeletePopupComponent } from './current-salary-delete-dialog.component';
import { ICurrentSalary } from 'app/shared/model/current-salary.model';

@Injectable({ providedIn: 'root' })
export class CurrentSalaryResolve implements Resolve<ICurrentSalary> {
    constructor(private service: CurrentSalaryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CurrentSalary> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CurrentSalary>) => response.ok),
                map((currentSalary: HttpResponse<CurrentSalary>) => currentSalary.body)
            );
        }
        return of(new CurrentSalary());
    }
}

export const currentSalaryRoute: Routes = [
    {
        path: 'current-salary',
        component: CurrentSalaryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CurrentSalaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'current-salary/:id/view',
        component: CurrentSalaryDetailComponent,
        resolve: {
            currentSalary: CurrentSalaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CurrentSalaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'current-salary/new',
        component: CurrentSalaryUpdateComponent,
        resolve: {
            currentSalary: CurrentSalaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CurrentSalaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'current-salary/:id/edit',
        component: CurrentSalaryUpdateComponent,
        resolve: {
            currentSalary: CurrentSalaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CurrentSalaries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const currentSalaryPopupRoute: Routes = [
    {
        path: 'current-salary/:id/delete',
        component: CurrentSalaryDeletePopupComponent,
        resolve: {
            currentSalary: CurrentSalaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CurrentSalaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
