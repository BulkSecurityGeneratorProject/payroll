import { Moment } from 'moment';

export interface IHRAHistory {
    id?: number;
    date?: Moment;
    cityCategory?: string;
    currentValue?: number;
}

export class HRAHistory implements IHRAHistory {
    constructor(public id?: number, public date?: Moment, public cityCategory?: string, public currentValue?: number) {}
}
