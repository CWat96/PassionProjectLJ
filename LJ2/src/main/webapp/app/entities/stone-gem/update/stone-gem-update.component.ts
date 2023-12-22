import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IStoneGem } from '../stone-gem.model';
import { StoneGemService } from '../service/stone-gem.service';
import { StoneGemFormService, StoneGemFormGroup } from './stone-gem-form.service';

@Component({
  standalone: true,
  selector: 'jhi-stone-gem-update',
  templateUrl: './stone-gem-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class StoneGemUpdateComponent implements OnInit {
  isSaving = false;
  stoneGem: IStoneGem | null = null;

  editForm: StoneGemFormGroup = this.stoneGemFormService.createStoneGemFormGroup();

  constructor(
    protected stoneGemService: StoneGemService,
    protected stoneGemFormService: StoneGemFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stoneGem }) => {
      this.stoneGem = stoneGem;
      if (stoneGem) {
        this.updateForm(stoneGem);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stoneGem = this.stoneGemFormService.getStoneGem(this.editForm);
    if (stoneGem.id !== null) {
      this.subscribeToSaveResponse(this.stoneGemService.update(stoneGem));
    } else {
      this.subscribeToSaveResponse(this.stoneGemService.create(stoneGem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStoneGem>>): void {
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

  protected updateForm(stoneGem: IStoneGem): void {
    this.stoneGem = stoneGem;
    this.stoneGemFormService.resetForm(this.editForm, stoneGem);
  }
}
