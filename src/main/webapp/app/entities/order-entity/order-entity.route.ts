import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrderEntity } from 'app/shared/model/order-entity.model';
import { OrderEntityService } from './order-entity.service';
import { OrderEntityComponent } from './order-entity.component';
import { OrderEntityDetailComponent } from './order-entity-detail.component';
import { OrderEntityUpdateComponent } from './order-entity-update.component';
import { OrderEntityDeletePopupComponent } from './order-entity-delete-dialog.component';
import { IOrderEntity } from 'app/shared/model/order-entity.model';

@Injectable({ providedIn: 'root' })
export class OrderEntityResolve implements Resolve<IOrderEntity> {
  constructor(private service: OrderEntityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrderEntity> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OrderEntity>) => response.ok),
        map((orderEntity: HttpResponse<OrderEntity>) => orderEntity.body)
      );
    }
    return of(new OrderEntity());
  }
}

export const orderEntityRoute: Routes = [
  {
    path: '',
    component: OrderEntityComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'storageManagementSpringApp.orderEntity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrderEntityDetailComponent,
    resolve: {
      orderEntity: OrderEntityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.orderEntity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrderEntityUpdateComponent,
    resolve: {
      orderEntity: OrderEntityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.orderEntity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrderEntityUpdateComponent,
    resolve: {
      orderEntity: OrderEntityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.orderEntity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderEntityPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OrderEntityDeletePopupComponent,
    resolve: {
      orderEntity: OrderEntityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'storageManagementSpringApp.orderEntity.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
