/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PayrollTestModule } from '../../../test.module';
import { CLAHistoryDetailComponent } from 'app/entities/cla-history/cla-history-detail.component';
import { CLAHistory } from 'app/shared/model/cla-history.model';

describe('Component Tests', () => {
    describe('CLAHistory Management Detail Component', () => {
        let comp: CLAHistoryDetailComponent;
        let fixture: ComponentFixture<CLAHistoryDetailComponent>;
        const route = ({ data: of({ cLAHistory: new CLAHistory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [CLAHistoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CLAHistoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CLAHistoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cLAHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
