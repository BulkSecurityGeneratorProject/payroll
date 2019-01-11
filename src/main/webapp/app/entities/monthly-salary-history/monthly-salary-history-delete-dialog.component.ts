import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMonthlySalaryHistory } from 'app/shared/model/monthly-salary-history.model';
import { MonthlySalaryHistoryService } from './monthly-salary-history.service';

@Component({
    selector: 'jhi-monthly-salary-history-delete-dialog',
    templateUrl: './monthly-salary-history-delete-dialog.component.html'
})
export class MonthlySalaryHistoryDeleteDialogComponent {
    monthlySalaryHistory: IMonthlySalaryHistory;

    constructor(
        protected monthlySalaryHistoryService: MonthlySalaryHistoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.monthlySalaryHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'monthlySalaryHistoryListModification',
                content: 'Deleted an monthlySalaryHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-monthly-salary-history-delete-popup',
    template: ''
})
export class MonthlySalaryHistoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monthlySalaryHistory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MonthlySalaryHistoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.monthlySalaryHistory = monthlySalaryHistory;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
