import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEffects } from 'app/entities/effects/effects.model';
import { EffectsService } from 'app/entities/effects/service/effects.service';
import { IMaterialSideEffects } from 'app/entities/material-side-effects/material-side-effects.model';
import { MaterialSideEffectsService } from 'app/entities/material-side-effects/service/material-side-effects.service';
import { PlatingMaterialService } from '../service/plating-material.service';
import { IPlatingMaterial } from '../plating-material.model';
import { PlatingMaterialFormService, PlatingMaterialFormGroup } from './plating-material-form.service';

@Component({
  standalone: true,
  selector: 'jhi-plating-material-update',
  templateUrl: './plating-material-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PlatingMaterialUpdateComponent implements OnInit {
  isSaving = false;
  platingMaterial: IPlatingMaterial | null = null;

  effectsSharedCollection: IEffects[] = [];
  materialSideEffectsSharedCollection: IMaterialSideEffects[] = [];

  editForm: PlatingMaterialFormGroup = this.platingMaterialFormService.createPlatingMaterialFormGroup();

  constructor(
    protected platingMaterialService: PlatingMaterialService,
    protected platingMaterialFormService: PlatingMaterialFormService,
    protected effectsService: EffectsService,
    protected materialSideEffectsService: MaterialSideEffectsService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEffects = (o1: IEffects | null, o2: IEffects | null): boolean => this.effectsService.compareEffects(o1, o2);

  compareMaterialSideEffects = (o1: IMaterialSideEffects | null, o2: IMaterialSideEffects | null): boolean =>
    this.materialSideEffectsService.compareMaterialSideEffects(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ platingMaterial }) => {
      this.platingMaterial = platingMaterial;
      if (platingMaterial) {
        this.updateForm(platingMaterial);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const platingMaterial = this.platingMaterialFormService.getPlatingMaterial(this.editForm);
    if (platingMaterial.id !== null) {
      this.subscribeToSaveResponse(this.platingMaterialService.update(platingMaterial));
    } else {
      this.subscribeToSaveResponse(this.platingMaterialService.create(platingMaterial));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlatingMaterial>>): void {
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

  protected updateForm(platingMaterial: IPlatingMaterial): void {
    this.platingMaterial = platingMaterial;
    this.platingMaterialFormService.resetForm(this.editForm, platingMaterial);

    this.effectsSharedCollection = this.effectsService.addEffectsToCollectionIfMissing<IEffects>(
      this.effectsSharedCollection,
      ...(platingMaterial.effects ?? []),
    );
    this.materialSideEffectsSharedCollection =
      this.materialSideEffectsService.addMaterialSideEffectsToCollectionIfMissing<IMaterialSideEffects>(
        this.materialSideEffectsSharedCollection,
        ...(platingMaterial.materialsideeffects ?? []),
      );
  }

  protected loadRelationshipsOptions(): void {
    this.effectsService
      .query()
      .pipe(map((res: HttpResponse<IEffects[]>) => res.body ?? []))
      .pipe(
        map((effects: IEffects[]) =>
          this.effectsService.addEffectsToCollectionIfMissing<IEffects>(effects, ...(this.platingMaterial?.effects ?? [])),
        ),
      )
      .subscribe((effects: IEffects[]) => (this.effectsSharedCollection = effects));

    this.materialSideEffectsService
      .query()
      .pipe(map((res: HttpResponse<IMaterialSideEffects[]>) => res.body ?? []))
      .pipe(
        map((materialSideEffects: IMaterialSideEffects[]) =>
          this.materialSideEffectsService.addMaterialSideEffectsToCollectionIfMissing<IMaterialSideEffects>(
            materialSideEffects,
            ...(this.platingMaterial?.materialsideeffects ?? []),
          ),
        ),
      )
      .subscribe((materialSideEffects: IMaterialSideEffects[]) => (this.materialSideEffectsSharedCollection = materialSideEffects));
  }
}
