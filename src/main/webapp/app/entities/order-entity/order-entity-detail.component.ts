import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderEntity, OrderEntity } from 'app/shared/model/order-entity.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { OrderItemService } from 'app/entities/order-item';
import { OrderEntityService } from 'app/entities/order-entity/order-entity.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { QueryConstants } from 'app/shared/constants/query.constants';

@Component({
  selector: 'jhi-order-entity-detail',
  templateUrl: './order-entity-detail.component.html'
})
export class OrderEntityDetailComponent implements OnInit {
  orderEntity: IOrderEntity;
  hasItems: boolean;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private eventManager: JhiEventManager,
    private orderEntityService: OrderEntityService,
    private orderItemService: OrderItemService,
    private jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderEntity }) => {
      this.orderEntity = orderEntity;
    });
  }

  previousState() {
    window.history.back();
  }

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }

  isFinalized(): boolean {
    return this.orderEntity.statusId === QueryConstants.orderStatus.LEZARVA;
  }

  onRaktarClick(orderEntity: OrderEntity) {
    this.orderEntityService.placeIntoProducts(orderEntity.orderItemList, orderEntity.id).subscribe(
      (response: HttpResponse<OrderEntity>) => {
        this.jhiAlertService.success('storageManagementApp.orderEntity.raktarbaFelveve', response.body.id, null);
        this.eventManager.broadcast({ name: 'orderEntityListModification', content: 'OK' });
      },
      (response: HttpErrorResponse) => this.onError(response.message)
    );
  }

  hasItemsEmit(value: boolean) {
    this.hasItems = value;
  }
}
