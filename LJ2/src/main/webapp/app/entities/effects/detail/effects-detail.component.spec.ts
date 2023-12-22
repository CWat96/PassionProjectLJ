import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EffectsDetailComponent } from './effects-detail.component';

describe('Effects Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EffectsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EffectsDetailComponent,
              resolve: { effects: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EffectsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load effects on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EffectsDetailComponent);

      // THEN
      expect(instance.effects).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
