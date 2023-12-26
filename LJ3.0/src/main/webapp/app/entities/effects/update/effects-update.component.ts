import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMaterialSideEffects } from 'app/entities/material-side-effects/material-side-effects.model';
import { MaterialSideEffectsService } from 'app/entities/material-side-effects/service/material-side-effects.service';
import { IEffects } from '../effects.model';
import { EffectsService } from '../service/effects.service';
import { EffectsFormService, EffectsFormGroup } from './effects-form.service';

@Component({
  standalone: true,
  selector: 'jhi-effects-update',
  templateUrl: './effects-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EffectsUpdateComponent implements OnInit {
  isSaving = false;
  effects: IEffects | null = null;

  materialSideEffectsSharedCollection: IMaterialSideEffects[] = [];

  editForm: EffectsFormGroup = this.effectsFormService.createEffectsFormGroup();

  constructor(
    protected effectsService: EffectsService,
    protected effectsFormService: EffectsFormService,
    protected materialSideEffectsService: MaterialSideEffectsService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareMaterialSideEffects = (o1: IMaterialSideEffects | null, o2: IMaterialSideEffects | null): boolean =>
    this.materialSideEffectsService.compareMaterialSideEffects(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ effects }) => {
      this.effects = effects;
      if (effects) {
        this.updateForm(effects);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const effects = this.effectsFormService.getEffects(this.editForm);
    if (effects.id !== null) {
      this.subscribeToSaveResponse(this.effectsService.update(effects));
    } else {
      this.subscribeToSaveResponse(this.effectsService.create(effects));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEffects>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(effects: IEffects): void {
    this.effects = effects;
    this.effectsFormService.resetForm(this.editForm, effects);

    this.materialSideEffectsSharedCollection =
      this.materialSideEffectsService.addMaterialSideEffectsToCollectionIfMissing<IMaterialSideEffects>(
        this.materialSideEffectsSharedCollection,
        ...(effects.effects ?? []),
      );
  }

  protected loadRelationshipsOptions(): void {
    this.materialSideEffectsService
      .query()
      .pipe(map((res: HttpResponse<IMaterialSideEffects[]>) => res.body ?? []))
      .pipe(
        map((materialSideEffects: IMaterialSideEffects[]) =>
          this.materialSideEffectsService.addMaterialSideEffectsToCollectionIfMissing<IMaterialSideEffects>(
            materialSideEffects,
            ...(this.effects?.effects ?? []),
          ),
        ),
      )
      .subscribe((materialSideEffects: IMaterialSideEffects[]) => (this.materialSideEffectsSharedCollection = materialSideEffects));
  }
}
