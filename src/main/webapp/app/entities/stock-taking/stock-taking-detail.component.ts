import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockTaking } from 'app/shared/model/stock-taking.model';

@Component({
  selector: 'jhi-stock-taking-detail',
  templateUrl: './stock-taking-detail.component.html'
})
export class StockTakingDetailComponent implements OnInit {
  stockTaking: IStockTaking;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockTaking }) => {
      this.stockTaking = stockTaking;
    });
  }

  previousState() {
    window.history.back();
  }
}
