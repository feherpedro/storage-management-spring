import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStockTakingItem, StockTakingItem } from 'app/shared/model/stock-taking-item.model';
import { StockTakingItemService } from './stock-taking-item.service';
import { IStockTaking } from 'app/shared/model/stock-taking.model';
import { StockTakingService } from 'app/entities/stock-taking';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status';

@Component({
  selector: 'jhi-stock-taking-item-update',
  templateUrl: './stock-taking-item-update.component.html'
})
export class StockTakingItemUpdateComponent implements OnInit {
  isSaving: boolean;

  stocktakings: IStockTaking[];

  products: IProduct[];

  statuses: IStatus[];

  editForm = this.fb.group({
    id: [],
    oldQuantity: [],
    newQuantity: [null, [Validators.required]],
    difference: [],
    stockTakingId: [],
    productId: [],
    statusId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected stockTakingItemService: StockTakingItemService,
    protected stockTakingService: StockTakingService,
    protected productService: ProductService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stockTakingItem }) => {
      this.updateForm(stockTakingItem);
    });
    this.stockTakingService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStockTaking[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStockTaking[]>) => response.body)
      )
      .subscribe((res: IStockTaking[]) => (this.stocktakings = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.statusService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStatus[]>) => response.body)
      )
      .subscribe((res: IStatus[]) => (this.statuses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(stockTakingItem: IStockTakingItem) {
    this.editForm.patchValue({
      id: stockTakingItem.id,
      oldQuantity: stockTakingItem.oldQuantity,
      newQuantity: stockTakingItem.newQuantity,
      difference: stockTakingItem.difference,
      stockTakingId: stockTakingItem.stockTakingId,
      productId: stockTakingItem.productId,
      statusId: stockTakingItem.statusId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const stockTakingItem = this.createFromForm();
    if (stockTakingItem.id !== undefined) {
      this.subscribeToSaveResponse(this.stockTakingItemService.update(stockTakingItem));
    } else {
      this.subscribeToSaveResponse(this.stockTakingItemService.create(stockTakingItem));
    }
  }

  private createFromForm(): IStockTakingItem {
    const entity = {
      ...new StockTakingItem(),
      id: this.editForm.get(['id']).value,
      oldQuantity: this.editForm.get(['oldQuantity']).value,
      newQuantity: this.editForm.get(['newQuantity']).value,
      difference: this.editForm.get(['difference']).value,
      stockTakingId: this.editForm.get(['stockTakingId']).value,
      productId: this.editForm.get(['productId']).value,
      statusId: this.editForm.get(['statusId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockTakingItem>>) {
    result.subscribe((res: HttpResponse<IStockTakingItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackStockTakingById(index: number, item: IStockTaking) {
    return item.id;
  }

  trackProductById(index: number, item: IProduct) {
    return item.id;
  }

  trackStatusById(index: number, item: IStatus) {
    return item.id;
  }
}
