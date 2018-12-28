/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PayrollTestModule } from '../../../test.module';
import { HRAHistoryDetailComponent } from 'app/entities/hra-history/hra-history-detail.component';
import { HRAHistory } from 'app/shared/model/hra-history.model';

describe('Component Tests', () => {
    describe('HRAHistory Management Detail Component', () => {
        let comp: HRAHistoryDetailComponent;
        let fixture: ComponentFixture<HRAHistoryDetailComponent>;
        const route = ({ data: of({ hRAHistory: new HRAHistory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [HRAHistoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HRAHistoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HRAHistoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.hRAHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
