import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PosterServerTestModule } from '../../../test.module';
import { BillInfoUpdateComponent } from 'app/entities/bill-info/bill-info-update.component';
import { BillInfoService } from 'app/entities/bill-info/bill-info.service';
import { BillInfo } from 'app/shared/model/bill-info.model';

describe('Component Tests', () => {
  describe('BillInfo Management Update Component', () => {
    let comp: BillInfoUpdateComponent;
    let fixture: ComponentFixture<BillInfoUpdateComponent>;
    let service: BillInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PosterServerTestModule],
        declarations: [BillInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BillInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BillInfo(123);
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
        const entity = new BillInfo();
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
