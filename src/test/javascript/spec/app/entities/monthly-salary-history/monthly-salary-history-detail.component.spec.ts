/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PayrollTestModule } from '../../../test.module';
import { MonthlySalaryHistoryDetailComponent } from 'app/entities/monthly-salary-history/monthly-salary-history-detail.component';
import { MonthlySalaryHistory } from 'app/shared/model/monthly-salary-history.model';

describe('Component Tests', () => {
    describe('MonthlySalaryHistory Management Detail Component', () => {
        let comp: MonthlySalaryHistoryDetailComponent;
        let fixture: ComponentFixture<MonthlySalaryHistoryDetailComponent>;
        const route = ({ data: of({ monthlySalaryHistory: new MonthlySalaryHistory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [MonthlySalaryHistoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MonthlySalaryHistoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MonthlySalaryHistoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.monthlySalaryHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
