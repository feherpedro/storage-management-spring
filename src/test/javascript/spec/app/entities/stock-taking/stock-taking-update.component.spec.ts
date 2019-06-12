/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { StorageManagementSpringTestModule } from '../../../test.module';
import { StockTakingUpdateComponent } from 'app/entities/stock-taking/stock-taking-update.component';
import { StockTakingService } from 'app/entities/stock-taking/stock-taking.service';
import { StockTaking } from 'app/shared/model/stock-taking.model';

describe('Component Tests', () => {
  describe('StockTaking Management Update Component', () => {
    let comp: StockTakingUpdateComponent;
    let fixture: ComponentFixture<StockTakingUpdateComponent>;
    let service: StockTakingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StorageManagementSpringTestModule],
        declarations: [StockTakingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StockTakingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockTakingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockTakingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StockTaking(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new StockTaking();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
