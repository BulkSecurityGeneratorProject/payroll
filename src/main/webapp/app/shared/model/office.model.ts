import { ICurrentSalary } from 'app/shared/model//current-salary.model';

export interface IOffice {
    id?: number;
    officeName?: string;
    city?: string;
    cityCategory?: string;
    pincode?: number;
    taluka?: string;
    district?: string;
    currentSalaries?: ICurrentSalary[];
}

export class Office implements IOffice {
    constructor(
        public id?: number,
        public officeName?: string,
        public city?: string,
        public cityCategory?: string,
        public pincode?: number,
        public taluka?: string,
        public district?: string,
        public currentSalaries?: ICurrentSalary[]
    ) {}
}
