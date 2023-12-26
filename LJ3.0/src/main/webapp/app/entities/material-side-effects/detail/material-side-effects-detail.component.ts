import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMaterialSideEffects } from '../material-side-effects.model';

@Component({
  standalone: true,
  selector: 'jhi-material-side-effects-detail',
  templateUrl: './material-side-effects-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MaterialSideEffectsDetailComponent {
  @Input() materialSideEffects: IMaterialSideEffects | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
