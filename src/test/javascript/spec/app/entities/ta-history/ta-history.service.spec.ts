/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TAHistoryService } from 'app/entities/ta-history/ta-history.service';
import { ITAHistory, TAHistory } from 'app/shared/model/ta-history.model';

describe('Service Tests', () => {
    describe('TAHistory Service', () => {
        let injector: TestBed;
        let service: TAHistoryService;
        let httpMock: HttpTestingController;
        let elemDefault: ITAHistory;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(TAHistoryService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new TAHistory(0, currentDate, 'AAAAAAA', 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        date: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a TAHistory', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        date: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        date: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new TAHistory(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a TAHistory', async () => {
                const returnedFromService = Object.assign(
                    {
                        date: currentDate.format(DATE_FORMAT),
                        cityCategory: 'BBBBBB',
                        currentValue: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        date: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of TAHistory', async () => {
                const returnedFromService = Object.assign(
                    {
                        date: currentDate.format(DATE_FORMAT),
                        cityCategory: 'BBBBBB',
                        currentValue: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        date: currentDate
                    },
                    returnedFromService
                );
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

            it('should delete a TAHistory', async () => {
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
