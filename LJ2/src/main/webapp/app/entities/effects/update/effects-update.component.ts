import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPlatingMaterial } from 'app/entities/plating-material/plating-material.model';
import { PlatingMaterialService } from 'app/entities/plating-material/service/plating-material.service';
import { IStoneGem } from 'app/entities/stone-gem/stone-gem.model';
import { StoneGemService } from 'app/entities/stone-gem/service/stone-gem.service';
import { EffectsService } from '../service/effects.service';
import { IEffects } from '../effects.model';
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

  platingMaterialsSharedCollection: IPlatingMaterial[] = [];
  stoneGemsSharedCollection: IStoneGem[] = [];

  editForm: EffectsFormGroup = this.effectsFormService.createEffectsFormGroup();

  constructor(
    protected effectsService: EffectsService,
    protected effectsFormService: EffectsFormService,
    protected platingMaterialService: PlatingMaterialService,
    protected stoneGemService: StoneGemService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  comparePlatingMaterial = (o1: IPlatingMaterial | null, o2: IPlatingMaterial | null): boolean =>
    this.platingMaterialService.comparePlatingMaterial(o1, o2);

  compareStoneGem = (o1: IStoneGem | null, o2: IStoneGem | null): boolean => this.stoneGemService.compareStoneGem(o1, o2);

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

    this.platingMaterialsSharedCollection = this.platingMaterialService.addPlatingMaterialToCollectionIfMissing<IPlatingMaterial>(
      this.platingMaterialsSharedCollection,
      effects.platingMaterial,
    );
    this.stoneGemsSharedCollection = this.stoneGemService.addStoneGemToCollectionIfMissing<IStoneGem>(
      this.stoneGemsSharedCollection,
      effects.stoneGem,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.platingMaterialService
      .query()
      .pipe(map((res: HttpResponse<IPlatingMaterial[]>) => res.body ?? []))
      .pipe(
        map((platingMaterials: IPlatingMaterial[]) =>
          this.platingMaterialService.addPlatingMaterialToCollectionIfMissing<IPlatingMaterial>(
            platingMaterials,
            this.effects?.platingMaterial,
          ),
        ),
      )
      .subscribe((platingMaterials: IPlatingMaterial[]) => (this.platingMaterialsSharedCollection = platingMaterials));

    this.stoneGemService
      .query()
      .pipe(map((res: HttpResponse<IStoneGem[]>) => res.body ?? []))
      .pipe(
        map((stoneGems: IStoneGem[]) =>
          this.stoneGemService.addStoneGemToCollectionIfMissing<IStoneGem>(stoneGems, this.effects?.stoneGem),
        ),
      )
      .subscribe((stoneGems: IStoneGem[]) => (this.stoneGemsSharedCollection = stoneGems));
  }
}
