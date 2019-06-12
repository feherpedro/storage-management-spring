import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPriceCategory } from 'app/shared/model/price-category.model';
import { PriceCategoryService } from './price-category.service';

@Component({
  selector: 'jhi-price-category-delete-dialog',
  templateUrl: './price-category-delete-dialog.component.html'
})
export class PriceCategoryDeleteDialogComponent {
  priceCategory: IPriceCategory;

  constructor(
    protected priceCategoryService: PriceCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.priceCategoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'priceCategoryListModification',
        content: 'Deleted an priceCategory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-price-category-delete-popup',
  template: ''
})
export class PriceCategoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ priceCategory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PriceCategoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.priceCategory = priceCategory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/price-category', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/price-category', { outlets: { popup: null } }]);
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
