/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { StorageManagementSpringTestModule } from '../../../test.module';
import { PriceCategoryUpdateComponent } from 'app/entities/price-category/price-category-update.component';
import { PriceCategoryService } from 'app/entities/price-category/price-category.service';
import { PriceCategory } from 'app/shared/model/price-category.model';

describe('Component Tests', () => {
  describe('PriceCategory Management Update Component', () => {
    let comp: PriceCategoryUpdateComponent;
    let fixture: ComponentFixture<PriceCategoryUpdateComponent>;
    let service: PriceCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StorageManagementSpringTestModule],
        declarations: [PriceCategoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PriceCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PriceCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PriceCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PriceCategory(123);
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
        const entity = new PriceCategory();
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
