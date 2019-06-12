/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StorageManagementSpringTestModule } from '../../../test.module';
import { OrderEntityDetailComponent } from 'app/entities/order-entity/order-entity-detail.component';
import { OrderEntity } from 'app/shared/model/order-entity.model';

describe('Component Tests', () => {
  describe('OrderEntity Management Detail Component', () => {
    let comp: OrderEntityDetailComponent;
    let fixture: ComponentFixture<OrderEntityDetailComponent>;
    const route = ({ data: of({ orderEntity: new OrderEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StorageManagementSpringTestModule],
        declarations: [OrderEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrderEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderEntityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderEntity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
