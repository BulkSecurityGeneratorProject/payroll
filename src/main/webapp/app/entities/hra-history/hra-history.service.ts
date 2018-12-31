import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHRAHistory } from 'app/shared/model/hra-history.model';

type EntityResponseType = HttpResponse<IHRAHistory>;
type EntityArrayResponseType = HttpResponse<IHRAHistory[]>;

@Injectable({ providedIn: 'root' })
export class HRAHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/hra-histories';

    constructor(protected http: HttpClient) {}

    create(hRAHistory: IHRAHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(hRAHistory);
        return this.http
            .post<IHRAHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(hRAHistory: IHRAHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(hRAHistory);
        return this.http
            .put<IHRAHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHRAHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHRAHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(hRAHistory: IHRAHistory): IHRAHistory {
        const copy: IHRAHistory = Object.assign({}, hRAHistory, {
            date: hRAHistory.date != null && hRAHistory.date.isValid() ? hRAHistory.date.format(DATE_FORMAT) : null
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
            res.body.forEach((hRAHistory: IHRAHistory) => {
                hRAHistory.date = hRAHistory.date != null ? moment(hRAHistory.date) : null;
            });
        }
        return res;
    }
}
