import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MaterialSideEffectsDetailComponent } from './material-side-effects-detail.component';

describe('MaterialSideEffects Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MaterialSideEffectsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MaterialSideEffectsDetailComponent,
              resolve: { materialSideEffects: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MaterialSideEffectsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load materialSideEffects on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MaterialSideEffectsDetailComponent);

      // THEN
      expect(instance.materialSideEffects).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
