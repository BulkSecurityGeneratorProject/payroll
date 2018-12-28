import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDAHistory } from 'app/shared/model/da-history.model';
import { DAHistoryService } from './da-history.service';

@Component({
    selector: 'jhi-da-history-delete-dialog',
    templateUrl: './da-history-delete-dialog.component.html'
})
export class DAHistoryDeleteDialogComponent {
    dAHistory: IDAHistory;

    constructor(
        protected dAHistoryService: DAHistoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dAHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dAHistoryListModification',
                content: 'Deleted an dAHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-da-history-delete-popup',
    template: ''
})
export class DAHistoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dAHistory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DAHistoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.dAHistory = dAHistory;
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
