/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { StorageManagementSpringTestModule } from '../../../test.module';
import { StockTakingItemUpdateComponent } from 'app/entities/stock-taking-item/stock-taking-item-update.component';
import { StockTakingItemService } from 'app/entities/stock-taking-item/stock-taking-item.service';
import { StockTakingItem } from 'app/shared/model/stock-taking-item.model';

describe('Component Tests', () => {
  describe('StockTakingItem Management Update Component', () => {
    let comp: StockTakingItemUpdateComponent;
    let fixture: ComponentFixture<StockTakingItemUpdateComponent>;
    let service: StockTakingItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StorageManagementSpringTestModule],
        declarations: [StockTakingItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StockTakingItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockTakingItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockTakingItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StockTakingItem(123);
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
        const entity = new StockTakingItem();
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
