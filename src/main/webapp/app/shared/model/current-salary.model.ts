export interface ICurrentSalary {
    id?: number;
    basicPay?: number;
    gradePay?: number;
    cityCategory?: string;
    facultyId?: number;
    officeOfficeName?: string;
    officeId?: number;
}

export class CurrentSalary implements ICurrentSalary {
    constructor(
        public id?: number,
        public basicPay?: number,
        public gradePay?: number,
        public cityCategory?: string,
        public facultyId?: number,
        public officeOfficeName?: string,
        public officeId?: number
    ) {}
}
