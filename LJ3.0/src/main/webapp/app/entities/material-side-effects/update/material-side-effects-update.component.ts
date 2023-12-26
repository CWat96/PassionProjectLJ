import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMaterialSideEffects } from '../material-side-effects.model';
import { MaterialSideEffectsService } from '../service/material-side-effects.service';
import { MaterialSideEffectsFormService, MaterialSideEffectsFormGroup } from './material-side-effects-form.service';

@Component({
  standalone: true,
  selector: 'jhi-material-side-effects-update',
  templateUrl: './material-side-effects-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MaterialSideEffectsUpdateComponent implements OnInit {
  isSaving = false;
  materialSideEffects: IMaterialSideEffects | null = null;

  editForm: MaterialSideEffectsFormGroup = this.materialSideEffectsFormService.createMaterialSideEffectsFormGroup();

  constructor(
    protected materialSideEffectsService: MaterialSideEffectsService,
    protected materialSideEffectsFormService: MaterialSideEffectsFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materialSideEffects }) => {
      this.materialSideEffects = materialSideEffects;
      if (materialSideEffects) {
        this.updateForm(materialSideEffects);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const materialSideEffects = this.materialSideEffectsFormService.getMaterialSideEffects(this.editForm);
    if (materialSideEffects.id !== null) {
      this.subscribeToSaveResponse(this.materialSideEffectsService.update(materialSideEffects));
    } else {
      this.subscribeToSaveResponse(this.materialSideEffectsService.create(materialSideEffects));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaterialSideEffects>>): void {
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

  protected updateForm(materialSideEffects: IMaterialSideEffects): void {
    this.materialSideEffects = materialSideEffects;
    this.materialSideEffectsFormService.resetForm(this.editForm, materialSideEffects);
  }
}
