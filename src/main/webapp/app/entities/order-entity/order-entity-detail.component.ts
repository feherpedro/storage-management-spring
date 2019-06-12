import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderEntity } from 'app/shared/model/order-entity.model';

@Component({
  selector: 'jhi-order-entity-detail',
  templateUrl: './order-entity-detail.component.html'
})
export class OrderEntityDetailComponent implements OnInit {
  orderEntity: IOrderEntity;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderEntity }) => {
      this.orderEntity = orderEntity;
    });
  }

  previousState() {
    window.history.back();
  }
}
