import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITAHistory } from 'app/shared/model/ta-history.model';
import { TAHistoryService } from './ta-history.service';

@Component({
    selector: 'jhi-ta-history-delete-dialog',
    templateUrl: './ta-history-delete-dialog.component.html'
})
export class TAHistoryDeleteDialogComponent {
    tAHistory: ITAHistory;

    constructor(
        protected tAHistoryService: TAHistoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tAHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tAHistoryListModification',
                content: 'Deleted an tAHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ta-history-delete-popup',
    template: ''
})
export class TAHistoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tAHistory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TAHistoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.tAHistory = tAHistory;
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
