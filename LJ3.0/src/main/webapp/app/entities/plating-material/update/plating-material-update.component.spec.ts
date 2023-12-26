import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEffects } from 'app/entities/effects/effects.model';
import { EffectsService } from 'app/entities/effects/service/effects.service';
import { IMaterialSideEffects } from 'app/entities/material-side-effects/material-side-effects.model';
import { MaterialSideEffectsService } from 'app/entities/material-side-effects/service/material-side-effects.service';
import { IPlatingMaterial } from '../plating-material.model';
import { PlatingMaterialService } from '../service/plating-material.service';
import { PlatingMaterialFormService } from './plating-material-form.service';

import { PlatingMaterialUpdateComponent } from './plating-material-update.component';

describe('PlatingMaterial Management Update Component', () => {
  let comp: PlatingMaterialUpdateComponent;
  let fixture: ComponentFixture<PlatingMaterialUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let platingMaterialFormService: PlatingMaterialFormService;
  let platingMaterialService: PlatingMaterialService;
  let effectsService: EffectsService;
  let materialSideEffectsService: MaterialSideEffectsService;

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
    effectsService = TestBed.inject(EffectsService);
    materialSideEffectsService = TestBed.inject(MaterialSideEffectsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Effects query and add missing value', () => {
      const platingMaterial: IPlatingMaterial = { id: 456 };
      const effects: IEffects[] = [{ id: 28844 }];
      platingMaterial.effects = effects;

      const effectsCollection: IEffects[] = [{ id: 3399 }];
      jest.spyOn(effectsService, 'query').mockReturnValue(of(new HttpResponse({ body: effectsCollection })));
      const additionalEffects = [...effects];
      const expectedCollection: IEffects[] = [...additionalEffects, ...effectsCollection];
      jest.spyOn(effectsService, 'addEffectsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ platingMaterial });
      comp.ngOnInit();

      expect(effectsService.query).toHaveBeenCalled();
      expect(effectsService.addEffectsToCollectionIfMissing).toHaveBeenCalledWith(
        effectsCollection,
        ...additionalEffects.map(expect.objectContaining),
      );
      expect(comp.effectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MaterialSideEffects query and add missing value', () => {
      const platingMaterial: IPlatingMaterial = { id: 456 };
      const materialsideeffects: IMaterialSideEffects[] = [{ id: 1362 }];
      platingMaterial.materialsideeffects = materialsideeffects;

      const materialSideEffectsCollection: IMaterialSideEffects[] = [{ id: 5148 }];
      jest.spyOn(materialSideEffectsService, 'query').mockReturnValue(of(new HttpResponse({ body: materialSideEffectsCollection })));
      const additionalMaterialSideEffects = [...materialsideeffects];
      const expectedCollection: IMaterialSideEffects[] = [...additionalMaterialSideEffects, ...materialSideEffectsCollection];
      jest.spyOn(materialSideEffectsService, 'addMaterialSideEffectsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ platingMaterial });
      comp.ngOnInit();

      expect(materialSideEffectsService.query).toHaveBeenCalled();
      expect(materialSideEffectsService.addMaterialSideEffectsToCollectionIfMissing).toHaveBeenCalledWith(
        materialSideEffectsCollection,
        ...additionalMaterialSideEffects.map(expect.objectContaining),
      );
      expect(comp.materialSideEffectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const platingMaterial: IPlatingMaterial = { id: 456 };
      const effects: IEffects = { id: 32709 };
      platingMaterial.effects = [effects];
      const materialsideeffects: IMaterialSideEffects = { id: 22500 };
      platingMaterial.materialsideeffects = [materialsideeffects];

      activatedRoute.data = of({ platingMaterial });
      comp.ngOnInit();

      expect(comp.effectsSharedCollection).toContain(effects);
      expect(comp.materialSideEffectsSharedCollection).toContain(materialsideeffects);
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

  describe('Compare relationships', () => {
    describe('compareEffects', () => {
      it('Should forward to effectsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(effectsService, 'compareEffects');
        comp.compareEffects(entity, entity2);
        expect(effectsService.compareEffects).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMaterialSideEffects', () => {
      it('Should forward to materialSideEffectsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(materialSideEffectsService, 'compareMaterialSideEffects');
        comp.compareMaterialSideEffects(entity, entity2);
        expect(materialSideEffectsService.compareMaterialSideEffects).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
