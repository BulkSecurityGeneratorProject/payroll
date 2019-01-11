import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICurrentSalary } from 'app/shared/model/current-salary.model';
import { CurrentSalaryService } from './current-salary.service';

@Component({
    selector: 'jhi-current-salary-delete-dialog',
    templateUrl: './current-salary-delete-dialog.component.html'
})
export class CurrentSalaryDeleteDialogComponent {
    currentSalary: ICurrentSalary;

    constructor(
        protected currentSalaryService: CurrentSalaryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.currentSalaryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'currentSalaryListModification',
                content: 'Deleted an currentSalary'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-current-salary-delete-popup',
    template: ''
})
export class CurrentSalaryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ currentSalary }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CurrentSalaryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.currentSalary = currentSalary;
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
