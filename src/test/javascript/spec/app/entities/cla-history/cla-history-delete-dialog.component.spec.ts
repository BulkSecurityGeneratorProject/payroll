/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollTestModule } from '../../../test.module';
import { CLAHistoryDeleteDialogComponent } from 'app/entities/cla-history/cla-history-delete-dialog.component';
import { CLAHistoryService } from 'app/entities/cla-history/cla-history.service';

describe('Component Tests', () => {
    describe('CLAHistory Management Delete Component', () => {
        let comp: CLAHistoryDeleteDialogComponent;
        let fixture: ComponentFixture<CLAHistoryDeleteDialogComponent>;
        let service: CLAHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [CLAHistoryDeleteDialogComponent]
            })
                .overrideTemplate(CLAHistoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CLAHistoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CLAHistoryService);
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
