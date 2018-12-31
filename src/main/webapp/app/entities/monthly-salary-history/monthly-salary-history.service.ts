import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMonthlySalaryHistory } from 'app/shared/model/monthly-salary-history.model';

type EntityResponseType = HttpResponse<IMonthlySalaryHistory>;
type EntityArrayResponseType = HttpResponse<IMonthlySalaryHistory[]>;

@Injectable({ providedIn: 'root' })
export class MonthlySalaryHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/monthly-salary-histories';

    constructor(protected http: HttpClient) {}

    create(monthlySalaryHistory: IMonthlySalaryHistory): Observable<EntityResponseType> {
        return this.http.post<IMonthlySalaryHistory>(this.resourceUrl, monthlySalaryHistory, { observe: 'response' });
    }

    update(monthlySalaryHistory: IMonthlySalaryHistory): Observable<EntityResponseType> {
        return this.http.put<IMonthlySalaryHistory>(this.resourceUrl, monthlySalaryHistory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMonthlySalaryHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMonthlySalaryHistory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
