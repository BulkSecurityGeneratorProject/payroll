import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICurrentSalary } from 'app/shared/model/current-salary.model';
import { CurrentSalaryService } from './current-salary.service';
import { IFaculty } from 'app/shared/model/faculty.model';
import { FacultyService } from 'app/entities/faculty';
import { IOffice } from 'app/shared/model/office.model';
import { OfficeService } from 'app/entities/office';

@Component({
    selector: 'jhi-current-salary-update',
    templateUrl: './current-salary-update.component.html'
})
export class CurrentSalaryUpdateComponent implements OnInit {
    currentSalary: ICurrentSalary;
    isSaving: boolean;

    faculties: IFaculty[];

    offices: IOffice[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected currentSalaryService: CurrentSalaryService,
        protected facultyService: FacultyService,
        protected officeService: OfficeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ currentSalary }) => {
            this.currentSalary = currentSalary;
        });
        this.facultyService.query().subscribe(
            (res: HttpResponse<IFaculty[]>) => {
                this.faculties = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.officeService.query().subscribe(
            (res: HttpResponse<IOffice[]>) => {
                this.offices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.currentSalary.id !== undefined) {
            this.subscribeToSaveResponse(this.currentSalaryService.update(this.currentSalary));
        } else {
            this.subscribeToSaveResponse(this.currentSalaryService.create(this.currentSalary));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrentSalary>>) {
        result.subscribe((res: HttpResponse<ICurrentSalary>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOfficeById(index: number, item: IOffice) {
        return item.id;
    }
}
