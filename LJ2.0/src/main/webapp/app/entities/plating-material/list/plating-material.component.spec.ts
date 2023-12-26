import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PlatingMaterialService } from '../service/plating-material.service';

import { PlatingMaterialComponent } from './plating-material.component';

describe('PlatingMaterial Management Component', () => {
  let comp: PlatingMaterialComponent;
  let fixture: ComponentFixture<PlatingMaterialComponent>;
  let service: PlatingMaterialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'plating-material', component: PlatingMaterialComponent }]),
        HttpClientTestingModule,
        PlatingMaterialComponent,
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
      .overrideTemplate(PlatingMaterialComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlatingMaterialComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PlatingMaterialService);

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
    expect(comp.platingMaterials?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to platingMaterialService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPlatingMaterialIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPlatingMaterialIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
