/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PayrollTestModule } from '../../../test.module';
import { CLAHistoryUpdateComponent } from 'app/entities/cla-history/cla-history-update.component';
import { CLAHistoryService } from 'app/entities/cla-history/cla-history.service';
import { CLAHistory } from 'app/shared/model/cla-history.model';

describe('Component Tests', () => {
    describe('CLAHistory Management Update Component', () => {
        let comp: CLAHistoryUpdateComponent;
        let fixture: ComponentFixture<CLAHistoryUpdateComponent>;
        let service: CLAHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [CLAHistoryUpdateComponent]
            })
                .overrideTemplate(CLAHistoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CLAHistoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CLAHistoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CLAHistory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.cLAHistory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CLAHistory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.cLAHistory = entity;
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
