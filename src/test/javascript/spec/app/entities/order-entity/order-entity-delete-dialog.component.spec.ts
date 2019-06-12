/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StorageManagementSpringTestModule } from '../../../test.module';
import { OrderEntityDeleteDialogComponent } from 'app/entities/order-entity/order-entity-delete-dialog.component';
import { OrderEntityService } from 'app/entities/order-entity/order-entity.service';

describe('Component Tests', () => {
  describe('OrderEntity Management Delete Component', () => {
    let comp: OrderEntityDeleteDialogComponent;
    let fixture: ComponentFixture<OrderEntityDeleteDialogComponent>;
    let service: OrderEntityService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StorageManagementSpringTestModule],
        declarations: [OrderEntityDeleteDialogComponent]
      })
        .overrideTemplate(OrderEntityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderEntityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderEntityService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
