import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICurrentSalary } from 'app/shared/model/current-salary.model';

type EntityResponseType = HttpResponse<ICurrentSalary>;
type EntityArrayResponseType = HttpResponse<ICurrentSalary[]>;

@Injectable({ providedIn: 'root' })
export class CurrentSalaryService {
    public resourceUrl = SERVER_API_URL + 'api/current-salaries';

    constructor(protected http: HttpClient) {}

    create(currentSalary: ICurrentSalary): Observable<EntityResponseType> {
        return this.http.post<ICurrentSalary>(this.resourceUrl, currentSalary, { observe: 'response' });
    }

    update(currentSalary: ICurrentSalary): Observable<EntityResponseType> {
        return this.http.put<ICurrentSalary>(this.resourceUrl, currentSalary, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICurrentSalary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICurrentSalary[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
