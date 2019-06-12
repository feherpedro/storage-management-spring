import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'product',
        loadChildren: './product/product.module#StorageManagementSpringProductModule'
      },
      {
        path: 'product-category',
        loadChildren: './product-category/product-category.module#StorageManagementSpringProductCategoryModule'
      },
      {
        path: 'price-category',
        loadChildren: './price-category/price-category.module#StorageManagementSpringPriceCategoryModule'
      },
      {
        path: 'status',
        loadChildren: './status/status.module#StorageManagementSpringStatusModule'
      },
      {
        path: 'order-entity',
        loadChildren: './order-entity/order-entity.module#StorageManagementSpringOrderEntityModule'
      },
      {
        path: 'order-item',
        loadChildren: './order-item/order-item.module#StorageManagementSpringOrderItemModule'
      },
      {
        path: 'stock-taking',
        loadChildren: './stock-taking/stock-taking.module#StorageManagementSpringStockTakingModule'
      },
      {
        path: 'stock-taking-item',
        loadChildren: './stock-taking-item/stock-taking-item.module#StorageManagementSpringStockTakingItemModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StorageManagementSpringEntityModule {}
