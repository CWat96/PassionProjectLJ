import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MaterialSideEffectsService } from '../service/material-side-effects.service';
import { IMaterialSideEffects } from '../material-side-effects.model';
import { MaterialSideEffectsFormService } from './material-side-effects-form.service';

import { MaterialSideEffectsUpdateComponent } from './material-side-effects-update.component';

describe('MaterialSideEffects Management Update Component', () => {
  let comp: MaterialSideEffectsUpdateComponent;
  let fixture: ComponentFixture<MaterialSideEffectsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let materialSideEffectsFormService: MaterialSideEffectsFormService;
  let materialSideEffectsService: MaterialSideEffectsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MaterialSideEffectsUpdateComponent],
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
      .overrideTemplate(MaterialSideEffectsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MaterialSideEffectsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    materialSideEffectsFormService = TestBed.inject(MaterialSideEffectsFormService);
    materialSideEffectsService = TestBed.inject(MaterialSideEffectsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const materialSideEffects: IMaterialSideEffects = { id: 456 };

      activatedRoute.data = of({ materialSideEffects });
      comp.ngOnInit();

      expect(comp.materialSideEffects).toEqual(materialSideEffects);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMaterialSideEffects>>();
      const materialSideEffects = { id: 123 };
      jest.spyOn(materialSideEffectsFormService, 'getMaterialSideEffects').mockReturnValue(materialSideEffects);
      jest.spyOn(materialSideEffectsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ materialSideEffects });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: materialSideEffects }));
      saveSubject.complete();

      // THEN
      expect(materialSideEffectsFormService.getMaterialSideEffects).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(materialSideEffectsService.update).toHaveBeenCalledWith(expect.objectContaining(materialSideEffects));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMaterialSideEffects>>();
      const materialSideEffects = { id: 123 };
      jest.spyOn(materialSideEffectsFormService, 'getMaterialSideEffects').mockReturnValue({ id: null });
      jest.spyOn(materialSideEffectsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ materialSideEffects: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: materialSideEffects }));
      saveSubject.complete();

      // THEN
      expect(materialSideEffectsFormService.getMaterialSideEffects).toHaveBeenCalled();
      expect(materialSideEffectsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMaterialSideEffects>>();
      const materialSideEffects = { id: 123 };
      jest.spyOn(materialSideEffectsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ materialSideEffects });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(materialSideEffectsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
