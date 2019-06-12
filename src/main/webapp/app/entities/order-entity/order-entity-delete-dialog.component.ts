import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderEntity } from 'app/shared/model/order-entity.model';
import { OrderEntityService } from './order-entity.service';

@Component({
  selector: 'jhi-order-entity-delete-dialog',
  templateUrl: './order-entity-delete-dialog.component.html'
})
export class OrderEntityDeleteDialogComponent {
  orderEntity: IOrderEntity;

  constructor(
    protected orderEntityService: OrderEntityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.orderEntityService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'orderEntityListModification',
        content: 'Deleted an orderEntity'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-order-entity-delete-popup',
  template: ''
})
export class OrderEntityDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderEntity }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OrderEntityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.orderEntity = orderEntity;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/order-entity', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/order-entity', { outlets: { popup: null } }]);
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
