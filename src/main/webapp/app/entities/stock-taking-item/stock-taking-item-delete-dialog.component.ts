import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockTakingItem } from 'app/shared/model/stock-taking-item.model';
import { StockTakingItemService } from './stock-taking-item.service';

@Component({
  selector: 'jhi-stock-taking-item-delete-dialog',
  templateUrl: './stock-taking-item-delete-dialog.component.html'
})
export class StockTakingItemDeleteDialogComponent {
  stockTakingItem: IStockTakingItem;

  constructor(
    protected stockTakingItemService: StockTakingItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stockTakingItemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'stockTakingItemListModification',
        content: 'Deleted an stockTakingItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-stock-taking-item-delete-popup',
  template: ''
})
export class StockTakingItemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockTakingItem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StockTakingItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.stockTakingItem = stockTakingItem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/stock-taking-item', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/stock-taking-item', { outlets: { popup: null } }]);
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
