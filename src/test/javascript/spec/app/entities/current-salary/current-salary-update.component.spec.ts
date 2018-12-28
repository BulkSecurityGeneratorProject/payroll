/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PayrollTestModule } from '../../../test.module';
import { CurrentSalaryUpdateComponent } from 'app/entities/current-salary/current-salary-update.component';
import { CurrentSalaryService } from 'app/entities/current-salary/current-salary.service';
import { CurrentSalary } from 'app/shared/model/current-salary.model';

describe('Component Tests', () => {
    describe('CurrentSalary Management Update Component', () => {
        let comp: CurrentSalaryUpdateComponent;
        let fixture: ComponentFixture<CurrentSalaryUpdateComponent>;
        let service: CurrentSalaryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [CurrentSalaryUpdateComponent]
            })
                .overrideTemplate(CurrentSalaryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CurrentSalaryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CurrentSalaryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CurrentSalary(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.currentSalary = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CurrentSalary();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.currentSalary = entity;
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
