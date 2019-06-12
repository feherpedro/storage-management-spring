import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockTakingItem } from 'app/shared/model/stock-taking-item.model';

@Component({
  selector: 'jhi-stock-taking-item-detail',
  templateUrl: './stock-taking-item-detail.component.html'
})
export class StockTakingItemDetailComponent implements OnInit {
  stockTakingItem: IStockTakingItem;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockTakingItem }) => {
      this.stockTakingItem = stockTakingItem;
    });
  }

  previousState() {
    window.history.back();
  }
}
