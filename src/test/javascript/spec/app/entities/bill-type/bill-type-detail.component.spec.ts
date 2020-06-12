import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PosterServerTestModule } from '../../../test.module';
import { BillTypeDetailComponent } from 'app/entities/bill-type/bill-type-detail.component';
import { BillType } from 'app/shared/model/bill-type.model';

describe('Component Tests', () => {
  describe('BillType Management Detail Component', () => {
    let comp: BillTypeDetailComponent;
    let fixture: ComponentFixture<BillTypeDetailComponent>;
    const route = ({ data: of({ billType: new BillType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PosterServerTestModule],
        declarations: [BillTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BillTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BillTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load billType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.billType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
