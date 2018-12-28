/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollTestModule } from '../../../test.module';
import { DAHistoryDeleteDialogComponent } from 'app/entities/da-history/da-history-delete-dialog.component';
import { DAHistoryService } from 'app/entities/da-history/da-history.service';

describe('Component Tests', () => {
    describe('DAHistory Management Delete Component', () => {
        let comp: DAHistoryDeleteDialogComponent;
        let fixture: ComponentFixture<DAHistoryDeleteDialogComponent>;
        let service: DAHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [DAHistoryDeleteDialogComponent]
            })
                .overrideTemplate(DAHistoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DAHistoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DAHistoryService);
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
