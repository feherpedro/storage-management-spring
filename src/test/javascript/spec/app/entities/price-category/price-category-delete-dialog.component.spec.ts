/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StorageManagementSpringTestModule } from '../../../test.module';
import { PriceCategoryDeleteDialogComponent } from 'app/entities/price-category/price-category-delete-dialog.component';
import { PriceCategoryService } from 'app/entities/price-category/price-category.service';

describe('Component Tests', () => {
  describe('PriceCategory Management Delete Component', () => {
    let comp: PriceCategoryDeleteDialogComponent;
    let fixture: ComponentFixture<PriceCategoryDeleteDialogComponent>;
    let service: PriceCategoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StorageManagementSpringTestModule],
        declarations: [PriceCategoryDeleteDialogComponent]
      })
        .overrideTemplate(PriceCategoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PriceCategoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PriceCategoryService);
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
