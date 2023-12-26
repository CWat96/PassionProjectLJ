import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MaterialSideEffectsService } from '../service/material-side-effects.service';

import { MaterialSideEffectsComponent } from './material-side-effects.component';

describe('MaterialSideEffects Management Component', () => {
  let comp: MaterialSideEffectsComponent;
  let fixture: ComponentFixture<MaterialSideEffectsComponent>;
  let service: MaterialSideEffectsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'material-side-effects', component: MaterialSideEffectsComponent }]),
        HttpClientTestingModule,
        MaterialSideEffectsComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(MaterialSideEffectsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MaterialSideEffectsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MaterialSideEffectsService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.materialSideEffects?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to materialSideEffectsService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getMaterialSideEffectsIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMaterialSideEffectsIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
