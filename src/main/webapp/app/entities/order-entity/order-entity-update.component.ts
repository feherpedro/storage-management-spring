import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IOrderEntity, OrderEntity } from 'app/shared/model/order-entity.model';
import { OrderEntityService } from './order-entity.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status';

@Component({
  selector: 'jhi-order-entity-update',
  templateUrl: './order-entity-update.component.html'
})
export class OrderEntityUpdateComponent implements OnInit {
  isSaving: boolean;

  statuses: IStatus[];
  createDateDp: any;
  paymentDateDp: any;
  dueDateDp: any;

  editForm = this.fb.group({
    id: [],
    createDate: [null, [Validators.required]],
    paymentDate: [],
    dueDate: [],
    statusId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected orderEntityService: OrderEntityService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orderEntity }) => {
      this.updateForm(orderEntity);
    });
    this.statusService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStatus[]>) => response.body)
      )
      .subscribe((res: IStatus[]) => (this.statuses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(orderEntity: IOrderEntity) {
    this.editForm.patchValue({
      id: orderEntity.id,
      createDate: orderEntity.createDate,
      paymentDate: orderEntity.paymentDate,
      dueDate: orderEntity.dueDate,
      statusId: orderEntity.statusId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const orderEntity = this.createFromForm();
    if (orderEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.orderEntityService.update(orderEntity));
    } else {
      this.subscribeToSaveResponse(this.orderEntityService.create(orderEntity));
    }
  }

  private createFromForm(): IOrderEntity {
    const entity = {
      ...new OrderEntity(),
      id: this.editForm.get(['id']).value,
      createDate: this.editForm.get(['createDate']).value,
      paymentDate: this.editForm.get(['paymentDate']).value,
      dueDate: this.editForm.get(['dueDate']).value,
      statusId: this.editForm.get(['statusId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderEntity>>) {
    result.subscribe((res: HttpResponse<IOrderEntity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackStatusById(index: number, item: IStatus) {
    return item.id;
  }
}
