import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOrderItem, OrderItem } from 'app/shared/model/order-item.model';
import { OrderItemService } from './order-item.service';
import { IOrderEntity } from 'app/shared/model/order-entity.model';
import { OrderEntityService } from 'app/entities/order-entity';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status';

@Component({
  selector: 'jhi-order-item-update',
  templateUrl: './order-item-update.component.html'
})
export class OrderItemUpdateComponent implements OnInit {
  isSaving: boolean;

  parentOrderId: number;
  orderentities: IOrderEntity[];

  products: IProduct[];

  statuses: IStatus[];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    orderEntityId: [{ value: this.parentOrderId, disabled: true }],
    productId: [null, [Validators.required]],
    statusId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected orderItemService: OrderItemService,
    protected orderEntityService: OrderEntityService,
    protected productService: ProductService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orderItem }) => {
      this.updateForm(orderItem);
    });
    this.activatedRoute.paramMap.subscribe(params => {
      const parent = params.get('parentId');
      if (parent != null) {
        this.parentOrderId = +parent;
      }
    });
    /*this.orderEntityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOrderEntity[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOrderEntity[]>) => response.body)
      )
      .subscribe((res: IOrderEntity[]) => (this.orderentities = res), (res: HttpErrorResponse) => this.onError(res.message));*/
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

  updateForm(orderItem: IOrderItem) {
    this.editForm.patchValue({
      id: orderItem.id,
      quantity: orderItem.quantity,
      orderEntityId: orderItem.orderEntityId,
      productId: orderItem.productId,
      statusId: orderItem.statusId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const orderItem = this.createFromForm();
    if (orderItem.id !== undefined) {
      this.subscribeToSaveResponse(this.orderItemService.update(orderItem));
    } else {
      this.subscribeToSaveResponse(this.orderItemService.create(orderItem));
    }
  }

  private createFromForm(): IOrderItem {
    const entity = {
      ...new OrderItem(),
      id: this.editForm.get(['id']).value,
      quantity: this.editForm.get(['quantity']).value,
      orderEntityId: this.parentOrderId,
      productId: this.editForm.get(['productId']).value,
      statusId: this.editForm.get(['statusId']).value
    }; /*this.editForm.get(['orderEntityId']).value*/
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderItem>>) {
    result.subscribe((res: HttpResponse<IOrderItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackOrderEntityById(index: number, item: IOrderEntity) {
    return item.id;
  }

  trackProductById(index: number, item: IProduct) {
    return item.id;
  }

  trackStatusById(index: number, item: IStatus) {
    return item.id;
  }
}
