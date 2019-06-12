import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { StorageManagementSpringSharedModule } from 'app/shared';
import {
  StockTakingItemComponent,
  StockTakingItemDetailComponent,
  StockTakingItemUpdateComponent,
  StockTakingItemDeletePopupComponent,
  StockTakingItemDeleteDialogComponent,
  stockTakingItemRoute,
  stockTakingItemPopupRoute
} from './';

const ENTITY_STATES = [...stockTakingItemRoute, ...stockTakingItemPopupRoute];

@NgModule({
  imports: [StorageManagementSpringSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StockTakingItemComponent,
    StockTakingItemDetailComponent,
    StockTakingItemUpdateComponent,
    StockTakingItemDeleteDialogComponent,
    StockTakingItemDeletePopupComponent
  ],
  entryComponents: [
    StockTakingItemComponent,
    StockTakingItemUpdateComponent,
    StockTakingItemDeleteDialogComponent,
    StockTakingItemDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StorageManagementSpringStockTakingItemModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
