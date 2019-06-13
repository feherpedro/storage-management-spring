import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { IPriceCategory } from 'app/shared/model/price-category.model';
import { PriceCategoryService } from 'app/entities/price-category';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  isSaving: boolean;

  productcategories: IProductCategory[];

  pricecategories: IPriceCategory[];

  statuses: IStatus[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    costPrice: [],
    price: [],
    quantity: [],
    unitOfMeasurement: [],
    manufacturer: [],
    barcode: [null, [Validators.required]],
    productCategoryId: [],
    priceCategoryId: [],
    statusId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productService: ProductService,
    protected productCategoryService: ProductCategoryService,
    protected priceCategoryService: PriceCategoryService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);
    });
    this.productCategoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProductCategory[]>) => response.body)
      )
      .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.priceCategoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPriceCategory[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPriceCategory[]>) => response.body)
      )
      .subscribe((res: IPriceCategory[]) => (this.pricecategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.statusService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStatus[]>) => response.body)
      )
      .subscribe((res: IStatus[]) => (this.statuses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(product: IProduct) {
    this.editForm.patchValue({
      id: product.id,
      name: product.name,
      costPrice: product.costPrice,
      price: product.price,
      quantity: product.quantity,
      unitOfMeasurement: product.unitOfMeasurement,
      manufacturer: product.manufacturer,
      barcode: product.barcode,
      productCategoryId: product.productCategoryId,
      priceCategoryId: product.priceCategoryId,
      statusId: product.statusId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    const entity = {
      ...new Product(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      costPrice: this.editForm.get(['costPrice']).value,
      price: this.editForm.get(['price']).value,
      quantity: this.editForm.get(['quantity']).value,
      unitOfMeasurement: this.editForm.get(['unitOfMeasurement']).value,
      manufacturer: this.editForm.get(['manufacturer']).value,
      barcode: this.editForm.get(['barcode']).value,
      productCategoryId: this.editForm.get(['productCategoryId']).value,
      priceCategoryId: this.editForm.get(['priceCategoryId']).value,
      statusId: this.editForm.get(['statusId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
    result.subscribe((res: HttpResponse<IProduct>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProductCategoryById(index: number, item: IProductCategory) {
    return item.id;
  }

  trackPriceCategoryById(index: number, item: IPriceCategory) {
    return item.id;
  }

  trackStatusById(index: number, item: IStatus) {
    return item.id;
  }
}
