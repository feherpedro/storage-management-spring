import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { StorageManagementSpringSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [StorageManagementSpringSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [StorageManagementSpringSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StorageManagementSpringSharedModule {
  static forRoot() {
    return {
      ngModule: StorageManagementSpringSharedModule
    };
  }
}
