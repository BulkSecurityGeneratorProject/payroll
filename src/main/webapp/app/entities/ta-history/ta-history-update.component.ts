import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { ITAHistory } from 'app/shared/model/ta-history.model';
import { TAHistoryService } from './ta-history.service';

@Component({
    selector: 'jhi-ta-history-update',
    templateUrl: './ta-history-update.component.html'
})
export class TAHistoryUpdateComponent implements OnInit {
    tAHistory: ITAHistory;
    isSaving: boolean;
    dateDp: any;

    constructor(protected tAHistoryService: TAHistoryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tAHistory }) => {
            this.tAHistory = tAHistory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tAHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.tAHistoryService.update(this.tAHistory));
        } else {
            this.subscribeToSaveResponse(this.tAHistoryService.create(this.tAHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITAHistory>>) {
        result.subscribe((res: HttpResponse<ITAHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
