/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PayrollTestModule } from '../../../test.module';
import { HRAHistoryUpdateComponent } from 'app/entities/hra-history/hra-history-update.component';
import { HRAHistoryService } from 'app/entities/hra-history/hra-history.service';
import { HRAHistory } from 'app/shared/model/hra-history.model';

describe('Component Tests', () => {
    describe('HRAHistory Management Update Component', () => {
        let comp: HRAHistoryUpdateComponent;
        let fixture: ComponentFixture<HRAHistoryUpdateComponent>;
        let service: HRAHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [HRAHistoryUpdateComponent]
            })
                .overrideTemplate(HRAHistoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HRAHistoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HRAHistoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new HRAHistory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.hRAHistory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new HRAHistory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.hRAHistory = entity;
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
