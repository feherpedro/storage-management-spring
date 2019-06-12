import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPriceCategory, PriceCategory } from 'app/shared/model/price-category.model';
import { PriceCategoryService } from './price-category.service';

@Component({
  selector: 'jhi-price-category-update',
  templateUrl: './price-category-update.component.html'
})
export class PriceCategoryUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    price: []
  });

  constructor(protected priceCategoryService: PriceCategoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ priceCategory }) => {
      this.updateForm(priceCategory);
    });
  }

  updateForm(priceCategory: IPriceCategory) {
    this.editForm.patchValue({
      id: priceCategory.id,
      name: priceCategory.name,
      price: priceCategory.price
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const priceCategory = this.createFromForm();
    if (priceCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.priceCategoryService.update(priceCategory));
    } else {
      this.subscribeToSaveResponse(this.priceCategoryService.create(priceCategory));
    }
  }

  private createFromForm(): IPriceCategory {
    const entity = {
      ...new PriceCategory(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      price: this.editForm.get(['price']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPriceCategory>>) {
    result.subscribe((res: HttpResponse<IPriceCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
