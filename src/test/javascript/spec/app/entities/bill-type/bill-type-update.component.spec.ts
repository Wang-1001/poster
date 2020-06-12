import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PosterServerTestModule } from '../../../test.module';
import { BillTypeUpdateComponent } from 'app/entities/bill-type/bill-type-update.component';
import { BillTypeService } from 'app/entities/bill-type/bill-type.service';
import { BillType } from 'app/shared/model/bill-type.model';

describe('Component Tests', () => {
  describe('BillType Management Update Component', () => {
    let comp: BillTypeUpdateComponent;
    let fixture: ComponentFixture<BillTypeUpdateComponent>;
    let service: BillTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PosterServerTestModule],
        declarations: [BillTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BillTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BillType(123);
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
        const entity = new BillType();
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
