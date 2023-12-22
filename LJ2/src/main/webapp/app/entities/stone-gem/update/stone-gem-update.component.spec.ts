import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const stoneGem: IStoneGem = { id: 456 };

      activatedRoute.data = of({ stoneGem });
      comp.ngOnInit();

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
});
