/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PayrollTestModule } from '../../../test.module';
import { TAHistoryUpdateComponent } from 'app/entities/ta-history/ta-history-update.component';
import { TAHistoryService } from 'app/entities/ta-history/ta-history.service';
import { TAHistory } from 'app/shared/model/ta-history.model';

describe('Component Tests', () => {
    describe('TAHistory Management Update Component', () => {
        let comp: TAHistoryUpdateComponent;
        let fixture: ComponentFixture<TAHistoryUpdateComponent>;
        let service: TAHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [TAHistoryUpdateComponent]
            })
                .overrideTemplate(TAHistoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TAHistoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TAHistoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TAHistory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tAHistory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TAHistory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tAHistory = entity;
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
