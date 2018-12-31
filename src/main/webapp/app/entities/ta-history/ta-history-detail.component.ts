import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITAHistory } from 'app/shared/model/ta-history.model';

@Component({
    selector: 'jhi-ta-history-detail',
    templateUrl: './ta-history-detail.component.html'
})
export class TAHistoryDetailComponent implements OnInit {
    tAHistory: ITAHistory;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tAHistory }) => {
            this.tAHistory = tAHistory;
        });
    }

    previousState() {
        window.history.back();
    }
}
