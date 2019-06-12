import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { StorageManagementSpringSharedModule } from 'app/shared';
import {
  PriceCategoryComponent,
  PriceCategoryDetailComponent,
  PriceCategoryUpdateComponent,
  PriceCategoryDeletePopupComponent,
  PriceCategoryDeleteDialogComponent,
  priceCategoryRoute,
  priceCategoryPopupRoute
} from './';

const ENTITY_STATES = [...priceCategoryRoute, ...priceCategoryPopupRoute];

@NgModule({
  imports: [StorageManagementSpringSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PriceCategoryComponent,
    PriceCategoryDetailComponent,
    PriceCategoryUpdateComponent,
    PriceCategoryDeleteDialogComponent,
    PriceCategoryDeletePopupComponent
  ],
  entryComponents: [
    PriceCategoryComponent,
    PriceCategoryUpdateComponent,
    PriceCategoryDeleteDialogComponent,
    PriceCategoryDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StorageManagementSpringPriceCategoryModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
