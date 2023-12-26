import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPlatingMaterial } from '../plating-material.model';
import { PlatingMaterialService } from '../service/plating-material.service';

@Component({
  standalone: true,
  templateUrl: './plating-material-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PlatingMaterialDeleteDialogComponent {
  platingMaterial?: IPlatingMaterial;

  constructor(
    protected platingMaterialService: PlatingMaterialService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.platingMaterialService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
