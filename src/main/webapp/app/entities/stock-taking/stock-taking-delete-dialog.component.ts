import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockTaking } from 'app/shared/model/stock-taking.model';
import { StockTakingService } from './stock-taking.service';

@Component({
  selector: 'jhi-stock-taking-delete-dialog',
  templateUrl: './stock-taking-delete-dialog.component.html'
})
export class StockTakingDeleteDialogComponent {
  stockTaking: IStockTaking;

  constructor(
    protected stockTakingService: StockTakingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stockTakingService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'stockTakingListModification',
        content: 'Deleted an stockTaking'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-stock-taking-delete-popup',
  template: ''
})
export class StockTakingDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockTaking }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StockTakingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.stockTaking = stockTaking;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/stock-taking', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/stock-taking', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
