import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHRAHistory } from 'app/shared/model/hra-history.model';

@Component({
    selector: 'jhi-hra-history-detail',
    templateUrl: './hra-history-detail.component.html'
})
export class HRAHistoryDetailComponent implements OnInit {
    hRAHistory: IHRAHistory;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hRAHistory }) => {
            this.hRAHistory = hRAHistory;
        });
    }

    previousState() {
        window.history.back();
    }
}
