import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { StoneGemService } from '../service/stone-gem.service';

import { StoneGemComponent } from './stone-gem.component';

describe('StoneGem Management Component', () => {
  let comp: StoneGemComponent;
  let fixture: ComponentFixture<StoneGemComponent>;
  let service: StoneGemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'stone-gem', component: StoneGemComponent }]),
        HttpClientTestingModule,
        StoneGemComponent,
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
      .overrideTemplate(StoneGemComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StoneGemComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(StoneGemService);

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
    expect(comp.stoneGems?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to stoneGemService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getStoneGemIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getStoneGemIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
