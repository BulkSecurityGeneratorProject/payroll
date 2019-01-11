import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICLAHistory } from 'app/shared/model/cla-history.model';

@Component({
    selector: 'jhi-cla-history-detail',
    templateUrl: './cla-history-detail.component.html'
})
export class CLAHistoryDetailComponent implements OnInit {
    cLAHistory: ICLAHistory;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cLAHistory }) => {
            this.cLAHistory = cLAHistory;
        });
    }

    previousState() {
        window.history.back();
    }
}
