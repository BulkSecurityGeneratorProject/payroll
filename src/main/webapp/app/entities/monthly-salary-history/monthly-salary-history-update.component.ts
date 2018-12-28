import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMonthlySalaryHistory } from 'app/shared/model/monthly-salary-history.model';
import { MonthlySalaryHistoryService } from './monthly-salary-history.service';
import { IFaculty } from 'app/shared/model/faculty.model';
import { FacultyService } from 'app/entities/faculty';

@Component({
    selector: 'jhi-monthly-salary-history-update',
    templateUrl: './monthly-salary-history-update.component.html'
})
export class MonthlySalaryHistoryUpdateComponent implements OnInit {
    monthlySalaryHistory: IMonthlySalaryHistory;
    isSaving: boolean;

    faculties: IFaculty[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected monthlySalaryHistoryService: MonthlySalaryHistoryService,
        protected facultyService: FacultyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ monthlySalaryHistory }) => {
            this.monthlySalaryHistory = monthlySalaryHistory;
        });
        this.facultyService.query().subscribe(
            (res: HttpResponse<IFaculty[]>) => {
                this.faculties = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.monthlySalaryHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.monthlySalaryHistoryService.update(this.monthlySalaryHistory));
        } else {
            this.subscribeToSaveResponse(this.monthlySalaryHistoryService.create(this.monthlySalaryHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMonthlySalaryHistory>>) {
        result.subscribe(
            (res: HttpResponse<IMonthlySalaryHistory>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackFacultyById(index: number, item: IFaculty) {
        return item.id;
    }
}
