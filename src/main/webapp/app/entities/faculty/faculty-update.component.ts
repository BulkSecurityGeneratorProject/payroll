import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFaculty } from 'app/shared/model/faculty.model';
import { FacultyService } from './faculty.service';
import { IUser, UserService } from 'app/core';
import { ICurrentSalary } from 'app/shared/model/current-salary.model';
import { CurrentSalaryService } from 'app/entities/current-salary';

@Component({
    selector: 'jhi-faculty-update',
    templateUrl: './faculty-update.component.html'
})
export class FacultyUpdateComponent implements OnInit {
    faculty: IFaculty;
    isSaving: boolean;

    users: IUser[];

    currentsalaries: ICurrentSalary[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected facultyService: FacultyService,
        protected userService: UserService,
        protected currentSalaryService: CurrentSalaryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ faculty }) => {
            this.faculty = faculty;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.currentSalaryService.query({ filter: 'faculty-is-null' }).subscribe(
            (res: HttpResponse<ICurrentSalary[]>) => {
                if (!this.faculty.currentSalaryId) {
                    this.currentsalaries = res.body;
                } else {
                    this.currentSalaryService.find(this.faculty.currentSalaryId).subscribe(
                        (subRes: HttpResponse<ICurrentSalary>) => {
                            this.currentsalaries = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.faculty.id !== undefined) {
            this.subscribeToSaveResponse(this.facultyService.update(this.faculty));
        } else {
            this.subscribeToSaveResponse(this.facultyService.create(this.faculty));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFaculty>>) {
        result.subscribe((res: HttpResponse<IFaculty>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackCurrentSalaryById(index: number, item: ICurrentSalary) {
        return item.id;
    }
}
