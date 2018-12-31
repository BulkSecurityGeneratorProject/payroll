/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { MonthlySalaryHistoryService } from 'app/entities/monthly-salary-history/monthly-salary-history.service';
import { IMonthlySalaryHistory, MonthlySalaryHistory } from 'app/shared/model/monthly-salary-history.model';

describe('Service Tests', () => {
    describe('MonthlySalaryHistory Service', () => {
        let injector: TestBed;
        let service: MonthlySalaryHistoryService;
        let httpMock: HttpTestingController;
        let elemDefault: IMonthlySalaryHistory;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(MonthlySalaryHistoryService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new MonthlySalaryHistory(0, 0, 0, 'AAAAAAA', 0, 0, 0, 0, 0, 0, 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a MonthlySalaryHistory', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new MonthlySalaryHistory(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a MonthlySalaryHistory', async () => {
                const returnedFromService = Object.assign(
                    {
                        year: 1,
                        month: 1,
                        officeName: 'BBBBBB',
                        basicPay: 1,
                        gradePay: 1,
                        basicTotal: 1,
                        daPercent: 1,
                        daValue: 1,
                        totalSalary: 1,
                        cityCategory: 'BBBBBB',
                        hraPercent: 1,
                        hraValue: 1,
                        travelAllowance: 1,
                        cla: 1,
                        medical: 1,
                        grossSalary: 1,
                        profTax: 1,
                        insurance: 1,
                        gpf: 1,
                        cpg: 1,
                        incometax: 1,
                        totalDeduction: 1,
                        netSalary: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of MonthlySalaryHistory', async () => {
                const returnedFromService = Object.assign(
                    {
                        year: 1,
                        month: 1,
                        officeName: 'BBBBBB',
                        basicPay: 1,
                        gradePay: 1,
                        basicTotal: 1,
                        daPercent: 1,
                        daValue: 1,
                        totalSalary: 1,
                        cityCategory: 'BBBBBB',
                        hraPercent: 1,
                        hraValue: 1,
                        travelAllowance: 1,
                        cla: 1,
                        medical: 1,
                        grossSalary: 1,
                        profTax: 1,
                        insurance: 1,
                        gpf: 1,
                        cpg: 1,
                        incometax: 1,
                        totalDeduction: 1,
                        netSalary: 1
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a MonthlySalaryHistory', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
