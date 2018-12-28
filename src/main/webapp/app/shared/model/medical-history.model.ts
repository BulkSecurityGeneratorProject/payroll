import { Moment } from 'moment';

export interface IMedicalHistory {
    id?: number;
    date?: Moment;
    currentValue?: number;
}

export class MedicalHistory implements IMedicalHistory {
    constructor(public id?: number, public date?: Moment, public currentValue?: number) {}
}
