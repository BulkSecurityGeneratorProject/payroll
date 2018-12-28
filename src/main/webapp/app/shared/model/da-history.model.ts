import { Moment } from 'moment';

export interface IDAHistory {
    id?: number;
    date?: Moment;
    increase?: number;
    currentValue?: number;
}

export class DAHistory implements IDAHistory {
    constructor(public id?: number, public date?: Moment, public increase?: number, public currentValue?: number) {}
}
