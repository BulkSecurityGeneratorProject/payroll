/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollTestModule } from '../../../test.module';
import { HRAHistoryDeleteDialogComponent } from 'app/entities/hra-history/hra-history-delete-dialog.component';
import { HRAHistoryService } from 'app/entities/hra-history/hra-history.service';

describe('Component Tests', () => {
    describe('HRAHistory Management Delete Component', () => {
        let comp: HRAHistoryDeleteDialogComponent;
        let fixture: ComponentFixture<HRAHistoryDeleteDialogComponent>;
        let service: HRAHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [HRAHistoryDeleteDialogComponent]
            })
                .overrideTemplate(HRAHistoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HRAHistoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HRAHistoryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
