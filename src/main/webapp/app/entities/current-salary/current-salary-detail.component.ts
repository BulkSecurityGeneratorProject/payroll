import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICurrentSalary } from 'app/shared/model/current-salary.model';

@Component({
    selector: 'jhi-current-salary-detail',
    templateUrl: './current-salary-detail.component.html'
})
export class CurrentSalaryDetailComponent implements OnInit {
    currentSalary: ICurrentSalary;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ currentSalary }) => {
            this.currentSalary = currentSalary;
        });
    }

    previousState() {
        window.history.back();
    }
}
