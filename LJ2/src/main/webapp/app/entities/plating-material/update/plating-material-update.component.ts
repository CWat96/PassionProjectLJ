import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPlatingMaterial } from '../plating-material.model';
import { PlatingMaterialService } from '../service/plating-material.service';
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

  editForm: PlatingMaterialFormGroup = this.platingMaterialFormService.createPlatingMaterialFormGroup();

  constructor(
    protected platingMaterialService: PlatingMaterialService,
    protected platingMaterialFormService: PlatingMaterialFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ platingMaterial }) => {
      this.platingMaterial = platingMaterial;
      if (platingMaterial) {
        this.updateForm(platingMaterial);
      }
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
  }
}
