import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMonthlySalaryHistory } from 'app/shared/model/monthly-salary-history.model';

@Component({
    selector: 'jhi-monthly-salary-history-detail',
    templateUrl: './monthly-salary-history-detail.component.html'
})
export class MonthlySalaryHistoryDetailComponent implements OnInit {
    monthlySalaryHistory: IMonthlySalaryHistory;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monthlySalaryHistory }) => {
            this.monthlySalaryHistory = monthlySalaryHistory;
        });
    }

    previousState() {
        window.history.back();
    }
}
