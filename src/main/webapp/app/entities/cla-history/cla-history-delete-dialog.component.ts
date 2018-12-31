import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICLAHistory } from 'app/shared/model/cla-history.model';
import { CLAHistoryService } from './cla-history.service';

@Component({
    selector: 'jhi-cla-history-delete-dialog',
    templateUrl: './cla-history-delete-dialog.component.html'
})
export class CLAHistoryDeleteDialogComponent {
    cLAHistory: ICLAHistory;

    constructor(
        protected cLAHistoryService: CLAHistoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cLAHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cLAHistoryListModification',
                content: 'Deleted an cLAHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cla-history-delete-popup',
    template: ''
})
export class CLAHistoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cLAHistory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CLAHistoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cLAHistory = cLAHistory;
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
