import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICLAHistory } from 'app/shared/model/cla-history.model';

type EntityResponseType = HttpResponse<ICLAHistory>;
type EntityArrayResponseType = HttpResponse<ICLAHistory[]>;

@Injectable({ providedIn: 'root' })
export class CLAHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/cla-histories';

    constructor(protected http: HttpClient) {}

    create(cLAHistory: ICLAHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cLAHistory);
        return this.http
            .post<ICLAHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(cLAHistory: ICLAHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cLAHistory);
        return this.http
            .put<ICLAHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICLAHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICLAHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(cLAHistory: ICLAHistory): ICLAHistory {
        const copy: ICLAHistory = Object.assign({}, cLAHistory, {
            date: cLAHistory.date != null && cLAHistory.date.isValid() ? cLAHistory.date.format(DATE_FORMAT) : null
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
            res.body.forEach((cLAHistory: ICLAHistory) => {
                cLAHistory.date = cLAHistory.date != null ? moment(cLAHistory.date) : null;
            });
        }
        return res;
    }
}
