/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StorageManagementSpringTestModule } from '../../../test.module';
import { PriceCategoryDetailComponent } from 'app/entities/price-category/price-category-detail.component';
import { PriceCategory } from 'app/shared/model/price-category.model';

describe('Component Tests', () => {
  describe('PriceCategory Management Detail Component', () => {
    let comp: PriceCategoryDetailComponent;
    let fixture: ComponentFixture<PriceCategoryDetailComponent>;
    const route = ({ data: of({ priceCategory: new PriceCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StorageManagementSpringTestModule],
        declarations: [PriceCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PriceCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PriceCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.priceCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
