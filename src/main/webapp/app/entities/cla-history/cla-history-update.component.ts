import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { ICLAHistory } from 'app/shared/model/cla-history.model';
import { CLAHistoryService } from './cla-history.service';

@Component({
    selector: 'jhi-cla-history-update',
    templateUrl: './cla-history-update.component.html'
})
export class CLAHistoryUpdateComponent implements OnInit {
    cLAHistory: ICLAHistory;
    isSaving: boolean;
    dateDp: any;

    constructor(protected cLAHistoryService: CLAHistoryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cLAHistory }) => {
            this.cLAHistory = cLAHistory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cLAHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.cLAHistoryService.update(this.cLAHistory));
        } else {
            this.subscribeToSaveResponse(this.cLAHistoryService.create(this.cLAHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICLAHistory>>) {
        result.subscribe((res: HttpResponse<ICLAHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
