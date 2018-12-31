/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PayrollTestModule } from '../../../test.module';
import { MonthlySalaryHistoryUpdateComponent } from 'app/entities/monthly-salary-history/monthly-salary-history-update.component';
import { MonthlySalaryHistoryService } from 'app/entities/monthly-salary-history/monthly-salary-history.service';
import { MonthlySalaryHistory } from 'app/shared/model/monthly-salary-history.model';

describe('Component Tests', () => {
    describe('MonthlySalaryHistory Management Update Component', () => {
        let comp: MonthlySalaryHistoryUpdateComponent;
        let fixture: ComponentFixture<MonthlySalaryHistoryUpdateComponent>;
        let service: MonthlySalaryHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [MonthlySalaryHistoryUpdateComponent]
            })
                .overrideTemplate(MonthlySalaryHistoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MonthlySalaryHistoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MonthlySalaryHistoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MonthlySalaryHistory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.monthlySalaryHistory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MonthlySalaryHistory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.monthlySalaryHistory = entity;
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
