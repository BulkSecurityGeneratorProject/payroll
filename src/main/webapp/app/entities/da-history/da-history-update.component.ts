import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IDAHistory } from 'app/shared/model/da-history.model';
import { DAHistoryService } from './da-history.service';

@Component({
    selector: 'jhi-da-history-update',
    templateUrl: './da-history-update.component.html'
})
export class DAHistoryUpdateComponent implements OnInit {
    dAHistory: IDAHistory;
    isSaving: boolean;
    dateDp: any;

    constructor(protected dAHistoryService: DAHistoryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dAHistory }) => {
            this.dAHistory = dAHistory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dAHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.dAHistoryService.update(this.dAHistory));
        } else {
            this.subscribeToSaveResponse(this.dAHistoryService.create(this.dAHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDAHistory>>) {
        result.subscribe((res: HttpResponse<IDAHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
