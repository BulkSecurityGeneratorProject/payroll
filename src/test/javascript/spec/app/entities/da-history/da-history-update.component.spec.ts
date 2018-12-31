/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PayrollTestModule } from '../../../test.module';
import { DAHistoryUpdateComponent } from 'app/entities/da-history/da-history-update.component';
import { DAHistoryService } from 'app/entities/da-history/da-history.service';
import { DAHistory } from 'app/shared/model/da-history.model';

describe('Component Tests', () => {
    describe('DAHistory Management Update Component', () => {
        let comp: DAHistoryUpdateComponent;
        let fixture: ComponentFixture<DAHistoryUpdateComponent>;
        let service: DAHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [DAHistoryUpdateComponent]
            })
                .overrideTemplate(DAHistoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DAHistoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DAHistoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DAHistory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dAHistory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DAHistory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dAHistory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
