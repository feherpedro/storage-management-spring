/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { StorageManagementSpringTestModule } from '../../../test.module';
import { OrderEntityUpdateComponent } from 'app/entities/order-entity/order-entity-update.component';
import { OrderEntityService } from 'app/entities/order-entity/order-entity.service';
import { OrderEntity } from 'app/shared/model/order-entity.model';

describe('Component Tests', () => {
  describe('OrderEntity Management Update Component', () => {
    let comp: OrderEntityUpdateComponent;
    let fixture: ComponentFixture<OrderEntityUpdateComponent>;
    let service: OrderEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StorageManagementSpringTestModule],
        declarations: [OrderEntityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrderEntityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderEntityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderEntityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderEntity(123);
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
        const entity = new OrderEntity();
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
