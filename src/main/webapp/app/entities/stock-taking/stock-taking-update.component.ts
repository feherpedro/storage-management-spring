import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IStockTaking, StockTaking } from 'app/shared/model/stock-taking.model';
import { StockTakingService } from './stock-taking.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status';

@Component({
  selector: 'jhi-stock-taking-update',
  templateUrl: './stock-taking-update.component.html'
})
export class StockTakingUpdateComponent implements OnInit {
  isSaving: boolean;

  statuses: IStatus[];
  stockTakingDateDp: any;

  editForm = this.fb.group({
    id: [],
    stockTakingDate: [null, [Validators.required]],
    statusId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected stockTakingService: StockTakingService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stockTaking }) => {
      this.updateForm(stockTaking);
    });
    this.statusService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStatus[]>) => response.body)
      )
      .subscribe((res: IStatus[]) => (this.statuses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(stockTaking: IStockTaking) {
    this.editForm.patchValue({
      id: stockTaking.id,
      stockTakingDate: stockTaking.stockTakingDate,
      statusId: stockTaking.statusId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const stockTaking = this.createFromForm();
    if (stockTaking.id !== undefined) {
      this.subscribeToSaveResponse(this.stockTakingService.update(stockTaking));
    } else {
      this.subscribeToSaveResponse(this.stockTakingService.create(stockTaking));
    }
  }

  private createFromForm(): IStockTaking {
    const entity = {
      ...new StockTaking(),
      id: this.editForm.get(['id']).value,
      stockTakingDate: this.editForm.get(['stockTakingDate']).value,
      statusId: this.editForm.get(['statusId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockTaking>>) {
    result.subscribe((res: HttpResponse<IStockTaking>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
