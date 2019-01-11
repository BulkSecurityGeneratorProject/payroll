import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDAHistory } from 'app/shared/model/da-history.model';

type EntityResponseType = HttpResponse<IDAHistory>;
type EntityArrayResponseType = HttpResponse<IDAHistory[]>;

@Injectable({ providedIn: 'root' })
export class DAHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/da-histories';

    constructor(protected http: HttpClient) {}

    create(dAHistory: IDAHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dAHistory);
        return this.http
            .post<IDAHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(dAHistory: IDAHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dAHistory);
        return this.http
            .put<IDAHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDAHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDAHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(dAHistory: IDAHistory): IDAHistory {
        const copy: IDAHistory = Object.assign({}, dAHistory, {
            date: dAHistory.date != null && dAHistory.date.isValid() ? dAHistory.date.format(DATE_FORMAT) : null
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
            res.body.forEach((dAHistory: IDAHistory) => {
                dAHistory.date = dAHistory.date != null ? moment(dAHistory.date) : null;
            });
        }
        return res;
    }
}
