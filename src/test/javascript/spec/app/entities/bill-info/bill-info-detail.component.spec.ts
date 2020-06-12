import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PosterServerTestModule } from '../../../test.module';
import { BillInfoDetailComponent } from 'app/entities/bill-info/bill-info-detail.component';
import { BillInfo } from 'app/shared/model/bill-info.model';

describe('Component Tests', () => {
  describe('BillInfo Management Detail Component', () => {
    let comp: BillInfoDetailComponent;
    let fixture: ComponentFixture<BillInfoDetailComponent>;
    const route = ({ data: of({ billInfo: new BillInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PosterServerTestModule],
        declarations: [BillInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BillInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BillInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load billInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.billInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
