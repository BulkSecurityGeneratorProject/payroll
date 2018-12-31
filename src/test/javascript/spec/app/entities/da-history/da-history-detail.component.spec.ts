/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PayrollTestModule } from '../../../test.module';
import { DAHistoryDetailComponent } from 'app/entities/da-history/da-history-detail.component';
import { DAHistory } from 'app/shared/model/da-history.model';

describe('Component Tests', () => {
    describe('DAHistory Management Detail Component', () => {
        let comp: DAHistoryDetailComponent;
        let fixture: ComponentFixture<DAHistoryDetailComponent>;
        const route = ({ data: of({ dAHistory: new DAHistory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [DAHistoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DAHistoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DAHistoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dAHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
