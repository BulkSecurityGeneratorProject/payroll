/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollTestModule } from '../../../test.module';
import { TAHistoryDeleteDialogComponent } from 'app/entities/ta-history/ta-history-delete-dialog.component';
import { TAHistoryService } from 'app/entities/ta-history/ta-history.service';

describe('Component Tests', () => {
    describe('TAHistory Management Delete Component', () => {
        let comp: TAHistoryDeleteDialogComponent;
        let fixture: ComponentFixture<TAHistoryDeleteDialogComponent>;
        let service: TAHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [TAHistoryDeleteDialogComponent]
            })
                .overrideTemplate(TAHistoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TAHistoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TAHistoryService);
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
