import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { StoneGemDetailComponent } from './stone-gem-detail.component';

describe('StoneGem Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StoneGemDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: StoneGemDetailComponent,
              resolve: { stoneGem: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(StoneGemDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load stoneGem on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', StoneGemDetailComponent);

      // THEN
      expect(instance.stoneGem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
