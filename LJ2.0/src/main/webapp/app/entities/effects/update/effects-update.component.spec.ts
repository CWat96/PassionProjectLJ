import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IMaterialSideEffects } from 'app/entities/material-side-effects/material-side-effects.model';
import { MaterialSideEffectsService } from 'app/entities/material-side-effects/service/material-side-effects.service';
import { EffectsService } from '../service/effects.service';
import { IEffects } from '../effects.model';
import { EffectsFormService } from './effects-form.service';

import { EffectsUpdateComponent } from './effects-update.component';

describe('Effects Management Update Component', () => {
  let comp: EffectsUpdateComponent;
  let fixture: ComponentFixture<EffectsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let effectsFormService: EffectsFormService;
  let effectsService: EffectsService;
  let materialSideEffectsService: MaterialSideEffectsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EffectsUpdateComponent],
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
      .overrideTemplate(EffectsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EffectsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    effectsFormService = TestBed.inject(EffectsFormService);
    effectsService = TestBed.inject(EffectsService);
    materialSideEffectsService = TestBed.inject(MaterialSideEffectsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MaterialSideEffects query and add missing value', () => {
      const effects: IEffects = { id: 456 };
      const effects: IMaterialSideEffects[] = [{ id: 18475 }];
      effects.effects = effects;

      const materialSideEffectsCollection: IMaterialSideEffects[] = [{ id: 12060 }];
      jest.spyOn(materialSideEffectsService, 'query').mockReturnValue(of(new HttpResponse({ body: materialSideEffectsCollection })));
      const additionalMaterialSideEffects = [...effects];
      const expectedCollection: IMaterialSideEffects[] = [...additionalMaterialSideEffects, ...materialSideEffectsCollection];
      jest.spyOn(materialSideEffectsService, 'addMaterialSideEffectsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ effects });
      comp.ngOnInit();

      expect(materialSideEffectsService.query).toHaveBeenCalled();
      expect(materialSideEffectsService.addMaterialSideEffectsToCollectionIfMissing).toHaveBeenCalledWith(
        materialSideEffectsCollection,
        ...additionalMaterialSideEffects.map(expect.objectContaining),
      );
      expect(comp.materialSideEffectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const effects: IEffects = { id: 456 };
      const effects: IMaterialSideEffects = { id: 16766 };
      effects.effects = [effects];

      activatedRoute.data = of({ effects });
      comp.ngOnInit();

      expect(comp.materialSideEffectsSharedCollection).toContain(effects);
      expect(comp.effects).toEqual(effects);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEffects>>();
      const effects = { id: 123 };
      jest.spyOn(effectsFormService, 'getEffects').mockReturnValue(effects);
      jest.spyOn(effectsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ effects });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: effects }));
      saveSubject.complete();

      // THEN
      expect(effectsFormService.getEffects).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(effectsService.update).toHaveBeenCalledWith(expect.objectContaining(effects));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEffects>>();
      const effects = { id: 123 };
      jest.spyOn(effectsFormService, 'getEffects').mockReturnValue({ id: null });
      jest.spyOn(effectsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ effects: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: effects }));
      saveSubject.complete();

      // THEN
      expect(effectsFormService.getEffects).toHaveBeenCalled();
      expect(effectsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEffects>>();
      const effects = { id: 123 };
      jest.spyOn(effectsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ effects });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(effectsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
