import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDAHistory } from 'app/shared/model/da-history.model';

@Component({
    selector: 'jhi-da-history-detail',
    templateUrl: './da-history-detail.component.html'
})
export class DAHistoryDetailComponent implements OnInit {
    dAHistory: IDAHistory;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dAHistory }) => {
            this.dAHistory = dAHistory;
        });
    }

    previousState() {
        window.history.back();
    }
}
