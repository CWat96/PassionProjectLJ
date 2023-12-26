import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PlatingMaterialDetailComponent } from './plating-material-detail.component';

describe('PlatingMaterial Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlatingMaterialDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PlatingMaterialDetailComponent,
              resolve: { platingMaterial: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PlatingMaterialDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load platingMaterial on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PlatingMaterialDetailComponent);

      // THEN
      expect(instance.platingMaterial).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
