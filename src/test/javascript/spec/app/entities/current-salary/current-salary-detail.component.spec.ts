/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PayrollTestModule } from '../../../test.module';
import { CurrentSalaryDetailComponent } from 'app/entities/current-salary/current-salary-detail.component';
import { CurrentSalary } from 'app/shared/model/current-salary.model';

describe('Component Tests', () => {
    describe('CurrentSalary Management Detail Component', () => {
        let comp: CurrentSalaryDetailComponent;
        let fixture: ComponentFixture<CurrentSalaryDetailComponent>;
        const route = ({ data: of({ currentSalary: new CurrentSalary(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [CurrentSalaryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CurrentSalaryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CurrentSalaryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.currentSalary).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
