import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IMedicalHistory } from 'app/shared/model/medical-history.model';
import { MedicalHistoryService } from './medical-history.service';

@Component({
    selector: 'jhi-medical-history-update',
    templateUrl: './medical-history-update.component.html'
})
export class MedicalHistoryUpdateComponent implements OnInit {
    medicalHistory: IMedicalHistory;
    isSaving: boolean;
    dateDp: any;

    constructor(protected medicalHistoryService: MedicalHistoryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ medicalHistory }) => {
            this.medicalHistory = medicalHistory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.medicalHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.medicalHistoryService.update(this.medicalHistory));
        } else {
            this.subscribeToSaveResponse(this.medicalHistoryService.create(this.medicalHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalHistory>>) {
        result.subscribe((res: HttpResponse<IMedicalHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
