import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IPlatingMaterial } from 'app/entities/plating-material/plating-material.model';
import { PlatingMaterialService } from 'app/entities/plating-material/service/plating-material.service';
import { IStoneGem } from 'app/entities/stone-gem/stone-gem.model';
import { StoneGemService } from 'app/entities/stone-gem/service/stone-gem.service';
import { IEffects } from '../effects.model';
import { EffectsService } from '../service/effects.service';
import { EffectsFormService } from './effects-form.service';

import { EffectsUpdateComponent } from './effects-update.component';

describe('Effects Management Update Component', () => {
  let comp: EffectsUpdateComponent;
  let fixture: ComponentFixture<EffectsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let effectsFormService: EffectsFormService;
  let effectsService: EffectsService;
  let platingMaterialService: PlatingMaterialService;
  let stoneGemService: StoneGemService;

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
    platingMaterialService = TestBed.inject(PlatingMaterialService);
    stoneGemService = TestBed.inject(StoneGemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PlatingMaterial query and add missing value', () => {
      const effects: IEffects = { id: 456 };
      const platingMaterial: IPlatingMaterial = { id: 5016 };
      effects.platingMaterial = platingMaterial;

      const platingMaterialCollection: IPlatingMaterial[] = [{ id: 12764 }];
      jest.spyOn(platingMaterialService, 'query').mockReturnValue(of(new HttpResponse({ body: platingMaterialCollection })));
      const additionalPlatingMaterials = [platingMaterial];
      const expectedCollection: IPlatingMaterial[] = [...additionalPlatingMaterials, ...platingMaterialCollection];
      jest.spyOn(platingMaterialService, 'addPlatingMaterialToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ effects });
      comp.ngOnInit();

      expect(platingMaterialService.query).toHaveBeenCalled();
      expect(platingMaterialService.addPlatingMaterialToCollectionIfMissing).toHaveBeenCalledWith(
        platingMaterialCollection,
        ...additionalPlatingMaterials.map(expect.objectContaining),
      );
      expect(comp.platingMaterialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call StoneGem query and add missing value', () => {
      const effects: IEffects = { id: 456 };
      const stoneGem: IStoneGem = { id: 22858 };
      effects.stoneGem = stoneGem;

      const stoneGemCollection: IStoneGem[] = [{ id: 23417 }];
      jest.spyOn(stoneGemService, 'query').mockReturnValue(of(new HttpResponse({ body: stoneGemCollection })));
      const additionalStoneGems = [stoneGem];
      const expectedCollection: IStoneGem[] = [...additionalStoneGems, ...stoneGemCollection];
      jest.spyOn(stoneGemService, 'addStoneGemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ effects });
      comp.ngOnInit();

      expect(stoneGemService.query).toHaveBeenCalled();
      expect(stoneGemService.addStoneGemToCollectionIfMissing).toHaveBeenCalledWith(
        stoneGemCollection,
        ...additionalStoneGems.map(expect.objectContaining),
      );
      expect(comp.stoneGemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const effects: IEffects = { id: 456 };
      const platingMaterial: IPlatingMaterial = { id: 1269 };
      effects.platingMaterial = platingMaterial;
      const stoneGem: IStoneGem = { id: 31161 };
      effects.stoneGem = stoneGem;

      activatedRoute.data = of({ effects });
      comp.ngOnInit();

      expect(comp.platingMaterialsSharedCollection).toContain(platingMaterial);
      expect(comp.stoneGemsSharedCollection).toContain(stoneGem);
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
    describe('comparePlatingMaterial', () => {
      it('Should forward to platingMaterialService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(platingMaterialService, 'comparePlatingMaterial');
        comp.comparePlatingMaterial(entity, entity2);
        expect(platingMaterialService.comparePlatingMaterial).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareStoneGem', () => {
      it('Should forward to stoneGemService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(stoneGemService, 'compareStoneGem');
        comp.compareStoneGem(entity, entity2);
        expect(stoneGemService.compareStoneGem).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
