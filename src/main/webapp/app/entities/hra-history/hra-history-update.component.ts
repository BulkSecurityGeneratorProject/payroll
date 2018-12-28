import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IHRAHistory } from 'app/shared/model/hra-history.model';
import { HRAHistoryService } from './hra-history.service';

@Component({
    selector: 'jhi-hra-history-update',
    templateUrl: './hra-history-update.component.html'
})
export class HRAHistoryUpdateComponent implements OnInit {
    hRAHistory: IHRAHistory;
    isSaving: boolean;
    dateDp: any;

    constructor(protected hRAHistoryService: HRAHistoryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hRAHistory }) => {
            this.hRAHistory = hRAHistory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hRAHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.hRAHistoryService.update(this.hRAHistory));
        } else {
            this.subscribeToSaveResponse(this.hRAHistoryService.create(this.hRAHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHRAHistory>>) {
        result.subscribe((res: HttpResponse<IHRAHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
