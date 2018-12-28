export interface IMonthlySalaryHistory {
    id?: number;
    year?: number;
    month?: number;
    officeName?: string;
    basicPay?: number;
    gradePay?: number;
    basicTotal?: number;
    daPercent?: number;
    daValue?: number;
    totalSalary?: number;
    cityCategory?: string;
    hraPercent?: number;
    hraValue?: number;
    travelAllowance?: number;
    cla?: number;
    medical?: number;
    grossSalary?: number;
    profTax?: number;
    insurance?: number;
    gpf?: number;
    cpg?: number;
    incometax?: number;
    totalDeduction?: number;
    netSalary?: number;
    facultyFirstName?: string;
    facultyId?: number;
}

export class MonthlySalaryHistory implements IMonthlySalaryHistory {
    constructor(
        public id?: number,
        public year?: number,
        public month?: number,
        public officeName?: string,
        public basicPay?: number,
        public gradePay?: number,
        public basicTotal?: number,
        public daPercent?: number,
        public daValue?: number,
        public totalSalary?: number,
        public cityCategory?: string,
        public hraPercent?: number,
        public hraValue?: number,
        public travelAllowance?: number,
        public cla?: number,
        public medical?: number,
        public grossSalary?: number,
        public profTax?: number,
        public insurance?: number,
        public gpf?: number,
        public cpg?: number,
        public incometax?: number,
        public totalDeduction?: number,
        public netSalary?: number,
        public facultyFirstName?: string,
        public facultyId?: number
    ) {}
}
