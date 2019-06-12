import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPriceCategory } from 'app/shared/model/price-category.model';

@Component({
  selector: 'jhi-price-category-detail',
  templateUrl: './price-category-detail.component.html'
})
export class PriceCategoryDetailComponent implements OnInit {
  priceCategory: IPriceCategory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ priceCategory }) => {
      this.priceCategory = priceCategory;
    });
  }

  previousState() {
    window.history.back();
  }
}
