import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEffects } from 'app/entities/effects/effects.model';
import { EffectsService } from 'app/entities/effects/service/effects.service';
import { StoneGemService } from '../service/stone-gem.service';
import { IStoneGem } from '../stone-gem.model';
import { StoneGemFormService } from './stone-gem-form.service';

import { StoneGemUpdateComponent } from './stone-gem-update.component';

describe('StoneGem Management Update Component', () => {
  let comp: StoneGemUpdateComponent;
  let fixture: ComponentFixture<StoneGemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let stoneGemFormService: StoneGemFormService;
  let stoneGemService: StoneGemService;
  let effectsService: EffectsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), StoneGemUpdateComponent],
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
      .overrideTemplate(StoneGemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StoneGemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    stoneGemFormService = TestBed.inject(StoneGemFormService);
    stoneGemService = TestBed.inject(StoneGemService);
    effectsService = TestBed.inject(EffectsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Effects query and add missing value', () => {
      const stoneGem: IStoneGem = { id: 456 };
      const effects: IEffects[] = [{ id: 19868 }];
      stoneGem.effects = effects;

      const effectsCollection: IEffects[] = [{ id: 3319 }];
      jest.spyOn(effectsService, 'query').mockReturnValue(of(new HttpResponse({ body: effectsCollection })));
      const additionalEffects = [...effects];
      const expectedCollection: IEffects[] = [...additionalEffects, ...effectsCollection];
      jest.spyOn(effectsService, 'addEffectsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ stoneGem });
      comp.ngOnInit();

      expect(effectsService.query).toHaveBeenCalled();
      expect(effectsService.addEffectsToCollectionIfMissing).toHaveBeenCalledWith(
        effectsCollection,
        ...additionalEffects.map(expect.objectContaining),
      );
      expect(comp.effectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const stoneGem: IStoneGem = { id: 456 };
      const effects: IEffects = { id: 20576 };
      stoneGem.effects = [effects];

      activatedRoute.data = of({ stoneGem });
      comp.ngOnInit();

      expect(comp.effectsSharedCollection).toContain(effects);
      expect(comp.stoneGem).toEqual(stoneGem);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStoneGem>>();
      const stoneGem = { id: 123 };
      jest.spyOn(stoneGemFormService, 'getStoneGem').mockReturnValue(stoneGem);
      jest.spyOn(stoneGemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stoneGem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stoneGem }));
      saveSubject.complete();

      // THEN
      expect(stoneGemFormService.getStoneGem).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(stoneGemService.update).toHaveBeenCalledWith(expect.objectContaining(stoneGem));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStoneGem>>();
      const stoneGem = { id: 123 };
      jest.spyOn(stoneGemFormService, 'getStoneGem').mockReturnValue({ id: null });
      jest.spyOn(stoneGemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stoneGem: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stoneGem }));
      saveSubject.complete();

      // THEN
      expect(stoneGemFormService.getStoneGem).toHaveBeenCalled();
      expect(stoneGemService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStoneGem>>();
      const stoneGem = { id: 123 };
      jest.spyOn(stoneGemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stoneGem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(stoneGemService.update).toHaveBeenCalled();
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
  });
});
