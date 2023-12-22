import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlatingMaterialService } from '../service/plating-material.service';
import { IPlatingMaterial } from '../plating-material.model';
import { PlatingMaterialFormService } from './plating-material-form.service';

import { PlatingMaterialUpdateComponent } from './plating-material-update.component';

describe('PlatingMaterial Management Update Component', () => {
  let comp: PlatingMaterialUpdateComponent;
  let fixture: ComponentFixture<PlatingMaterialUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let platingMaterialFormService: PlatingMaterialFormService;
  let platingMaterialService: PlatingMaterialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), PlatingMaterialUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PlatingMaterialUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlatingMaterialUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    platingMaterialFormService = TestBed.inject(PlatingMaterialFormService);
    platingMaterialService = TestBed.inject(PlatingMaterialService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const platingMaterial: IPlatingMaterial = { id: 456 };

      activatedRoute.data = of({ platingMaterial });
      comp.ngOnInit();

      expect(comp.platingMaterial).toEqual(platingMaterial);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlatingMaterial>>();
      const platingMaterial = { id: 123 };
      jest.spyOn(platingMaterialFormService, 'getPlatingMaterial').mockReturnValue(platingMaterial);
      jest.spyOn(platingMaterialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ platingMaterial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: platingMaterial }));
      saveSubject.complete();

      // THEN
      expect(platingMaterialFormService.getPlatingMaterial).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(platingMaterialService.update).toHaveBeenCalledWith(expect.objectContaining(platingMaterial));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlatingMaterial>>();
      const platingMaterial = { id: 123 };
      jest.spyOn(platingMaterialFormService, 'getPlatingMaterial').mockReturnValue({ id: null });
      jest.spyOn(platingMaterialService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ platingMaterial: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: platingMaterial }));
      saveSubject.complete();

      // THEN
      expect(platingMaterialFormService.getPlatingMaterial).toHaveBeenCalled();
      expect(platingMaterialService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlatingMaterial>>();
      const platingMaterial = { id: 123 };
      jest.spyOn(platingMaterialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ platingMaterial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(platingMaterialService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
