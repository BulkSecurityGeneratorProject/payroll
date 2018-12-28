import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHRAHistory } from 'app/shared/model/hra-history.model';
import { HRAHistoryService } from './hra-history.service';

@Component({
    selector: 'jhi-hra-history-delete-dialog',
    templateUrl: './hra-history-delete-dialog.component.html'
})
export class HRAHistoryDeleteDialogComponent {
    hRAHistory: IHRAHistory;

    constructor(
        protected hRAHistoryService: HRAHistoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hRAHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'hRAHistoryListModification',
                content: 'Deleted an hRAHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hra-history-delete-popup',
    template: ''
})
export class HRAHistoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hRAHistory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HRAHistoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.hRAHistory = hRAHistory;
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
