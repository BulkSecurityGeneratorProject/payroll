import { Moment } from 'moment';

export interface ITAHistory {
    id?: number;
    date?: Moment;
    cityCategory?: string;
    currentValue?: number;
}

export class TAHistory implements ITAHistory {
    constructor(public id?: number, public date?: Moment, public cityCategory?: string, public currentValue?: number) {}
}
