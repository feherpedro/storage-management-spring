/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StorageManagementSpringTestModule } from '../../../test.module';
import { StockTakingItemDetailComponent } from 'app/entities/stock-taking-item/stock-taking-item-detail.component';
import { StockTakingItem } from 'app/shared/model/stock-taking-item.model';

describe('Component Tests', () => {
  describe('StockTakingItem Management Detail Component', () => {
    let comp: StockTakingItemDetailComponent;
    let fixture: ComponentFixture<StockTakingItemDetailComponent>;
    const route = ({ data: of({ stockTakingItem: new StockTakingItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StorageManagementSpringTestModule],
        declarations: [StockTakingItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StockTakingItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockTakingItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stockTakingItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
