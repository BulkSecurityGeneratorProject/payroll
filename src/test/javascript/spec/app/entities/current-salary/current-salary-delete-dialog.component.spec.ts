/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollTestModule } from '../../../test.module';
import { CurrentSalaryDeleteDialogComponent } from 'app/entities/current-salary/current-salary-delete-dialog.component';
import { CurrentSalaryService } from 'app/entities/current-salary/current-salary.service';

describe('Component Tests', () => {
    describe('CurrentSalary Management Delete Component', () => {
        let comp: CurrentSalaryDeleteDialogComponent;
        let fixture: ComponentFixture<CurrentSalaryDeleteDialogComponent>;
        let service: CurrentSalaryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [CurrentSalaryDeleteDialogComponent]
            })
                .overrideTemplate(CurrentSalaryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CurrentSalaryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CurrentSalaryService);
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
