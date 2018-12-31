import { IMonthlySalaryHistory } from 'app/shared/model//monthly-salary-history.model';

export interface IFaculty {
    id?: number;
    firstName?: string;
    lastName?: string;
    designation?: string;
    userLogin?: string;
    userId?: number;
    currentSalaryId?: number;
    monthlySalaryHistories?: IMonthlySalaryHistory[];
}

export class Faculty implements IFaculty {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public designation?: string,
        public userLogin?: string,
        public userId?: number,
        public currentSalaryId?: number,
        public monthlySalaryHistories?: IMonthlySalaryHistory[]
    ) {}
}
