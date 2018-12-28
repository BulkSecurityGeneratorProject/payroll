import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMedicalHistory } from 'app/shared/model/medical-history.model';

type EntityResponseType = HttpResponse<IMedicalHistory>;
type EntityArrayResponseType = HttpResponse<IMedicalHistory[]>;

@Injectable({ providedIn: 'root' })
export class MedicalHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/medical-histories';

    constructor(protected http: HttpClient) {}

    create(medicalHistory: IMedicalHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(medicalHistory);
        return this.http
            .post<IMedicalHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(medicalHistory: IMedicalHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(medicalHistory);
        return this.http
            .put<IMedicalHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMedicalHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMedicalHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(medicalHistory: IMedicalHistory): IMedicalHistory {
        const copy: IMedicalHistory = Object.assign({}, medicalHistory, {
            date: medicalHistory.date != null && medicalHistory.date.isValid() ? medicalHistory.date.format(DATE_FORMAT) : null
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
            res.body.forEach((medicalHistory: IMedicalHistory) => {
                medicalHistory.date = medicalHistory.date != null ? moment(medicalHistory.date) : null;
            });
        }
        return res;
    }
}
