/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StorageManagementSpringTestModule } from '../../../test.module';
import { StockTakingDetailComponent } from 'app/entities/stock-taking/stock-taking-detail.component';
import { StockTaking } from 'app/shared/model/stock-taking.model';

describe('Component Tests', () => {
  describe('StockTaking Management Detail Component', () => {
    let comp: StockTakingDetailComponent;
    let fixture: ComponentFixture<StockTakingDetailComponent>;
    const route = ({ data: of({ stockTaking: new StockTaking(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StorageManagementSpringTestModule],
        declarations: [StockTakingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StockTakingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockTakingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stockTaking).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
