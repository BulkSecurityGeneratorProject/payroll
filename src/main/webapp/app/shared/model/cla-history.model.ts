import { Moment } from 'moment';

export interface ICLAHistory {
    id?: number;
    date?: Moment;
    cityCategory?: string;
    currentValue?: number;
}

export class CLAHistory implements ICLAHistory {
    constructor(public id?: number, public date?: Moment, public cityCategory?: string, public currentValue?: number) {}
}
