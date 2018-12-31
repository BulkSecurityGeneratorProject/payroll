/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PayrollTestModule } from '../../../test.module';
import { TAHistoryDetailComponent } from 'app/entities/ta-history/ta-history-detail.component';
import { TAHistory } from 'app/shared/model/ta-history.model';

describe('Component Tests', () => {
    describe('TAHistory Management Detail Component', () => {
        let comp: TAHistoryDetailComponent;
        let fixture: ComponentFixture<TAHistoryDetailComponent>;
        const route = ({ data: of({ tAHistory: new TAHistory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PayrollTestModule],
                declarations: [TAHistoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TAHistoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TAHistoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tAHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
