import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StockTaking } from 'app/shared/model/stock-taking.model';
import { StockTakingService } from './stock-taking.service';
import { StockTakingComponent } from './stock-taking.component';
import { StockTakingDetailComponent } from './stock-taking-detail.component';
import { StockTakingUpdateComponent } from './stock-taking-update.component';
import { StockTakingDeletePopupComponent } from './stock-taking-delete-dialog.component';
import { IStockTaking } from 'app/shared/model/stock-taking.model';

@Injectable({ providedIn: 'root' })
export class StockTakingResolve implements Resolve<IStockTaking> {
  constructor(private service: StockTakingService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStockTaking> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<StockTaking>) => response.ok),
        map((stockTaking: HttpResponse<StockTaking>) => stockTaking.body)
      );
    }
    return of(new StockTaking());
  }
}

export const stockTakingRoute: Routes = [
  {
    path: '',
    component: StockTakingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'storageManagementSpringApp.stockTaking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockTakingDetailComponent,
    resolve: {
      stockTaking: StockTakingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.stockTaking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockTakingUpdateComponent,
    resolve: {
      stockTaking: StockTakingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.stockTaking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockTakingUpdateComponent,
    resolve: {
      stockTaking: StockTakingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.stockTaking.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const stockTakingPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StockTakingDeletePopupComponent,
    resolve: {
      stockTaking: StockTakingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.stockTaking.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
