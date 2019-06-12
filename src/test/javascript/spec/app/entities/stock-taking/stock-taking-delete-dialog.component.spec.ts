/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StorageManagementSpringTestModule } from '../../../test.module';
import { StockTakingDeleteDialogComponent } from 'app/entities/stock-taking/stock-taking-delete-dialog.component';
import { StockTakingService } from 'app/entities/stock-taking/stock-taking.service';

describe('Component Tests', () => {
  describe('StockTaking Management Delete Component', () => {
    let comp: StockTakingDeleteDialogComponent;
    let fixture: ComponentFixture<StockTakingDeleteDialogComponent>;
    let service: StockTakingService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StorageManagementSpringTestModule],
        declarations: [StockTakingDeleteDialogComponent]
      })
        .overrideTemplate(StockTakingDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockTakingDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockTakingService);
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
