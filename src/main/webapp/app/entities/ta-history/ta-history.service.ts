import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITAHistory } from 'app/shared/model/ta-history.model';

type EntityResponseType = HttpResponse<ITAHistory>;
type EntityArrayResponseType = HttpResponse<ITAHistory[]>;

@Injectable({ providedIn: 'root' })
export class TAHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/ta-histories';

    constructor(protected http: HttpClient) {}

    create(tAHistory: ITAHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(tAHistory);
        return this.http
            .post<ITAHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(tAHistory: ITAHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(tAHistory);
        return this.http
            .put<ITAHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITAHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITAHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(tAHistory: ITAHistory): ITAHistory {
        const copy: ITAHistory = Object.assign({}, tAHistory, {
            date: tAHistory.date != null && tAHistory.date.isValid() ? tAHistory.date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((tAHistory: ITAHistory) => {
                tAHistory.date = tAHistory.date != null ? moment(tAHistory.date) : null;
            });
        }
        return res;
    }
}
