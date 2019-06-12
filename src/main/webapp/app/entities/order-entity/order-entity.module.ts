import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { StorageManagementSpringSharedModule } from 'app/shared';
import {
  OrderEntityComponent,
  OrderEntityDetailComponent,
  OrderEntityUpdateComponent,
  OrderEntityDeletePopupComponent,
  OrderEntityDeleteDialogComponent,
  orderEntityRoute,
  orderEntityPopupRoute
} from './';

const ENTITY_STATES = [...orderEntityRoute, ...orderEntityPopupRoute];

@NgModule({
  imports: [StorageManagementSpringSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrderEntityComponent,
    OrderEntityDetailComponent,
    OrderEntityUpdateComponent,
    OrderEntityDeleteDialogComponent,
    OrderEntityDeletePopupComponent
  ],
  entryComponents: [OrderEntityComponent, OrderEntityUpdateComponent, OrderEntityDeleteDialogComponent, OrderEntityDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StorageManagementSpringOrderEntityModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
