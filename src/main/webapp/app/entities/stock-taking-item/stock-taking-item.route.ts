import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StockTakingItem } from 'app/shared/model/stock-taking-item.model';
import { StockTakingItemService } from './stock-taking-item.service';
import { StockTakingItemComponent } from './stock-taking-item.component';
import { StockTakingItemDetailComponent } from './stock-taking-item-detail.component';
import { StockTakingItemUpdateComponent } from './stock-taking-item-update.component';
import { StockTakingItemDeletePopupComponent } from './stock-taking-item-delete-dialog.component';
import { IStockTakingItem } from 'app/shared/model/stock-taking-item.model';

@Injectable({ providedIn: 'root' })
export class StockTakingItemResolve implements Resolve<IStockTakingItem> {
  constructor(private service: StockTakingItemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStockTakingItem> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<StockTakingItem>) => response.ok),
        map((stockTakingItem: HttpResponse<StockTakingItem>) => stockTakingItem.body)
      );
    }
    return of(new StockTakingItem());
  }
}

export const stockTakingItemRoute: Routes = [
  {
    path: '',
    component: StockTakingItemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'storageManagementSpringApp.stockTakingItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockTakingItemDetailComponent,
    resolve: {
      stockTakingItem: StockTakingItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.stockTakingItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockTakingItemUpdateComponent,
    resolve: {
      stockTakingItem: StockTakingItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.stockTakingItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockTakingItemUpdateComponent,
    resolve: {
      stockTakingItem: StockTakingItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.stockTakingItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const stockTakingItemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StockTakingItemDeletePopupComponent,
    resolve: {
      stockTakingItem: StockTakingItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.stockTakingItem.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
