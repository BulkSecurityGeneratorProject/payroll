import { NgModule } from '@angular/core';

import { PayrollSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [PayrollSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [PayrollSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PayrollSharedCommonModule {}
